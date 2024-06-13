package pl.bcpr.cps.logic.model.simulator;

import pl.bcpr.cps.logic.model.signal.*;

public class DistanceSensor {

    private final double probeSignalTerm;
    private final double sampleRate;
    private final int bufferLength;
    private final double reportTerm;
    private final double signalVelocity;

    private DiscreteSignal discreteProbeSignal;
    private DiscreteSignal discreteFeedbackSignal;
    private DiscreteSignal correlationSignal;
    private double lastCalculationTimestamp = -Double.MAX_VALUE;
    private double distance;

    public DistanceSensor(double probeSignalTerm, double sampleRate,
                          int bufferLength, double reportTerm, double signalVelocity) {
        this.probeSignalTerm = probeSignalTerm;
        this.sampleRate = sampleRate;
        this.bufferLength = bufferLength;
        this.reportTerm = reportTerm;
        this.signalVelocity = signalVelocity;
    }

    public final ContinuousSignal generateProbeSignal() {
        return new OperationResultContinuousSignal(
                new SinusoidalSignal(0.0, 0.0, 1.0, probeSignalTerm),
                new SinusoidalRectifiedOneHalfSignal(0.0, 0.0, 2.0, probeSignalTerm),
                (a, b) -> a + b);
    }

    public DiscreteSignal getDiscreteProbeSignal() {
        return discreteProbeSignal;
    }

    public DiscreteSignal getDiscreteFeedbackSignal() {
        return discreteFeedbackSignal;
    }

    public DiscreteSignal getCorrelationSignal() {
        return correlationSignal;
    }

    public double getDistance() {
        return distance;
    }

    public void update(ContinuousSignal feedbackSignal, double timestamp) {
        double rangeStart = timestamp - bufferLength / sampleRate;
        this.discreteProbeSignal = new ContinuousBasedDiscreteSignal(rangeStart, bufferLength,
                sampleRate, generateProbeSignal());
        this.discreteFeedbackSignal = new ContinuousBasedDiscreteSignal(rangeStart, bufferLength,
                sampleRate, feedbackSignal);

        if (timestamp - lastCalculationTimestamp >= reportTerm) {
            lastCalculationTimestamp = timestamp;
            calculateDistance();
        }
    }

    private void calculateDistance() {
        this.correlationSignal = new CorrelationSignal(discreteFeedbackSignal, discreteProbeSignal);

        int indexOfFirstMax = correlationSignal.getNumberOfSamples() / 2;
        for (int i = indexOfFirstMax + 1; i < correlationSignal.getNumberOfSamples(); i++) {
            if (correlationSignal.value(i) > correlationSignal.value(indexOfFirstMax)) {
                indexOfFirstMax = i;
            }
        }

        double delay = (indexOfFirstMax - correlationSignal.getNumberOfSamples() / 2)
                / sampleRate;
        this.distance = delay * signalVelocity / 2.0;
    }
}
