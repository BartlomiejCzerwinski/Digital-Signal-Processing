package pl.bcpr.cps.logic.model.signal;

import pl.bcpr.cps.logic.model.data.Data;

import java.util.ArrayList;
import java.util.List;

public abstract class DiscreteSignal extends Signal {

    private final double sampleRate;
    private final int numberOfSamples;

    public DiscreteSignal(double rangeStart, double rangeLength, double sampleRate) {
        super(rangeStart, rangeLength);
        this.sampleRate = sampleRate;
        this.numberOfSamples = (int) (rangeLength * sampleRate);
    }

    public DiscreteSignal(double rangeStart, int numberOfSamples, double sampleRate) {
        super(rangeStart, numberOfSamples * (1.0 / sampleRate));
        this.sampleRate = sampleRate;
        this.numberOfSamples = numberOfSamples;
    }

    public double argument(int i) {
        return i * (1.0 / sampleRate) + getRangeStart();
    }

    public double getSampleRate() {
        return sampleRate;
    }

    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    public abstract double value(int i);

    @Override
    public List<Data> generateDiscreteRepresentation() {
        List<Data> data = new ArrayList<>();
        for (int i = 0; i < numberOfSamples; i++) {
            data.add(new Data(argument(i), value(i)));
        }
        return data;
    }
}
