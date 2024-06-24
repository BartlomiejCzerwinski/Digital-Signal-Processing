package pl.bcpr.cps.logic.model.transform;

public class DiscreteWaveletTransform extends RealTransform {

    private static final double[] DB4 = {
            (1.0 + Math.sqrt(3.0)) / (4.0 * Math.sqrt(2)),
            (3.0 + Math.sqrt(3.0)) / (4.0 * Math.sqrt(2)),
            (3.0 - Math.sqrt(3.0)) / (4.0 * Math.sqrt(2)),
            (1.0 - Math.sqrt(3.0)) / (4.0 * Math.sqrt(2))
    };


    @Override
    public double[] transform(final double[] x) {
        return waveletTransform(x, DB4);
    }

    public double[] waveletTransform(final double[] x, final double[] h) {
        int N = x.length;
        double[] X = new double[N];
        for (int i = 0; i < N; i++) {
            double sum = 0.0;
            int begin = (i / 2) * 2;
            for (int j = begin; j < begin + h.length; j++) {
                double factor;
                if (i % 2 == 0) {
                    factor = h[(j - begin)];
                } else {
                    factor = h[(h.length - (j - begin) - 1)];
                    if ((j - begin) % 2 == 1) {
                        factor *= -1;
                    }
                }
                sum += (factor * x[j % N]);
            }
            X[i] = sum;
        }
        double[] mixedX = new double[N];
        int iterator = 0;
        for (int i = 0; i < N; i += 2) {
            mixedX[iterator++] = X[i];
        }
        for (int i = 1; i < N; i += 2) {
            mixedX[iterator++] = X[i];
        }
        return mixedX;
    }
}
