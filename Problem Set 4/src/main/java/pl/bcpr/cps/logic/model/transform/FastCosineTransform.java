package pl.bcpr.cps.logic.model.transform;

import org.apache.commons.math3.complex.Complex;

public class FastCosineTransform extends RealTransform {

    private FastFourierTransform fastFourierTransform = new FastFourierTransform();

    @Override
    public double[] transform(final double[] x) {
        int N = x.length;

        double[] y = new double[N];
        for (int i = 0; i < N / 2; i++) {
            y[i] = x[2 * i];
            y[N - 1 - i] = x[2 * i + 1];
        }

        Complex[] fft = fastFourierTransform.transform(y);

        double Warg = -Math.PI / (2.0 * N);
        Complex W = new Complex(Math.cos(Warg), Math.sin(Warg));
        double[] X = new double[N];
        for (int m = 0; m < N; m++) {
            X[m] = W.pow(m).multiply(c(m, N)).multiply(fft[m]).getReal();
        }

        return X;
    }

    private double c(int m, int N) {
        if (m == 0) {
            return Math.sqrt(1.0 / N);
        } else {
            return Math.sqrt(2.0 / N);
        }
    }
}
