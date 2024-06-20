package pl.bcpr.cps.logic.model.transform;

public class WalshHadamardTransform extends RealTransform {

    @Override
    public double[] transform(final double[] x) {
        int m = (int) Math.round(Math.log(x.length) / Math.log(2.0));
        double[] H = generateHadamardMatrix(m);
        double[] X = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            double sum = 0.0;
            for (int j = 0; j < x.length; j++) {
                sum += x[j] * H[i * x.length + j];
            }
            X[i] = sum;
        }
        return X;
    }

    protected double[] generateHadamardMatrix(int m) {
        int size = 1;
        double factor = 1.0;
        double[] H = {1.0};
        double[] previous;
        for (int i = 1; i <= m; i++) {
            size *= 2;
            previous = H;
            H = new double[size * size];
            pasteMatrixIntoMatrix(previous, size / 2, H, size, 0, 0, factor);
            pasteMatrixIntoMatrix(previous, size / 2, H, size, 0, size / 2, factor);
            pasteMatrixIntoMatrix(previous, size / 2, H, size, size / 2, 0, factor);
            pasteMatrixIntoMatrix(previous, size / 2, H, size, size / 2, size / 2, -factor);
        }
        return H;
    }

    protected void pasteMatrixIntoMatrix(double[] src, int srcSize, double[] dst, int dstSize,
                                         int row, int col,
                                         double factor) {
        for (int i = 0; i < srcSize; i++) {
            for (int j = 0; j < srcSize; j++) {
                dst[(i + row) * dstSize + (j + col)] = src[i * srcSize + j] * factor;
            }
        }
    }
}
