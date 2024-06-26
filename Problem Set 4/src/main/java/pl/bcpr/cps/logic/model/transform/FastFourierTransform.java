package pl.bcpr.cps.logic.model.transform;

import org.apache.commons.math3.complex.Complex;

public class FastFourierTransform extends ComplexTransform {

    @Override
    public Complex[] transform(Complex[] x) {
        x = padToPowerOfTwo(x);
        Complex[] W = calculateVectorOfWParams(x.length);

        for (int N = x.length; N >= 2; N /= 2) {
            for (int i = 0; i < x.length / N; i++) {
                for (int m = 0; m < N / 2; m++) {
                    /* butterfly */
                    int offset = i * N;
                    Complex tmp = x[offset + m].subtract(x[offset + m + N / 2]);
                    x[offset + m] = x[offset + m].add(x[offset + m + N / 2]);
                    x[offset + m + N / 2] = tmp.multiply(retrieveWFromVector(N, -m, W));
                }
            }
        }

        mixSamples(x);

        return x;
    }

    protected Complex retrieveWFromVector(int N, int k, Complex[] vectorW) {
        k = k % N;
        if (k < 0) {
            k += N;
        }

        k = k * ((vectorW.length * 2) / N);
        if (k < vectorW.length) {
            return vectorW[k];
        } else {
            return vectorW[k - vectorW.length].multiply(new Complex(-1));
        }
    }

    protected Complex[] calculateVectorOfWParams(int N) {
        double Warg = 2.0 * Math.PI / N;
        Complex W = new Complex(Math.cos(Warg), Math.sin(Warg));
        Complex[] allW = new Complex[N / 2];
        for (int i = 0; i < allW.length; i++) {
            allW[i] = W.pow(i);
        }
        return allW;
    }

    protected void mixSamples(Complex[] samples) {
        int numberOfBits = 0;
        for (int i = 0; i < 32; i++) {
            if (((0x01 << i) & samples.length) != 0) {
                numberOfBits = i;
                break;
            }
        }

        for (int i = 0; i < samples.length; i++) {
            int newIndex = reverseBits(i, numberOfBits);
            if (newIndex > i) {
                Complex tmp = samples[i];
                samples[i] = samples[newIndex];
                samples[newIndex] = tmp;
            }
        }
    }

    protected int reverseBits(int value, int numberOfBits) {
        for (int i = 0; i < numberOfBits / 2; i++) {
            int j = numberOfBits - i - 1;
            if (((value >> i) & 0x01) != ((value >> j) & 0x01)) {
                value ^= ((0x01 << i) | (0x01 << j));
            }
        }
        return value;
    }

    private Complex[] padToPowerOfTwo(Complex[] x) {
        int N = x.length;
        int newLength = 1;
        while (newLength < N) {
            newLength *= 2;
        }
        if (newLength == N) {
            return x;
        }
        Complex[] padded = new Complex[newLength];
        System.arraycopy(x, 0, padded, 0, N);
        for (int i = N; i < newLength; i++) {
            padded[i] = new Complex(0, 0);
        }
        return padded;
    }
}
