package pl.bcpr.cps.logic.model.signal;

import org.apache.commons.math3.complex.Complex;
import pl.bcpr.cps.logic.model.data.Data;

import java.util.ArrayList;
import java.util.List;

public abstract class DiscreteComplexSignal extends ComplexSignal {

    private final double sampleRate;
    private final int numberOfSamples;

    public DiscreteComplexSignal(double rangeStart, double rangeLength, double sampleRate) {
        super(rangeStart, rangeLength);
        this.sampleRate = sampleRate;
        /* how many whole samples does this rangeLength contain */
        this.numberOfSamples = (int) (rangeLength * sampleRate);
    }

    public DiscreteComplexSignal(double rangeStart, int numberOfSamples, double sampleRate) {
        super(rangeStart, numberOfSamples * (1.0 / sampleRate));
        this.sampleRate = sampleRate;
        this.numberOfSamples = numberOfSamples;
    }

    public double getSampleRate() {
        return sampleRate;
    }

    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    public abstract Complex value(int n);

    public double argument(int n) {
        return n * sampleRate / numberOfSamples;
    }

    @Override
    public List<Data> generateDiscreteRepresentation() {
        List<Data> data = new ArrayList<>();
        for (int i = 0; i < numberOfSamples; i++) {
            double value;
            switch (getDiscreteRepresentationType()) {
                case ABS:
                    value = value(i).abs();
                    break;
                case ARG:
                    value = value(i).getArgument();
                    break;
                case REAL:
                    value = value(i).getReal();
                    break;
                default /*IMAGINARY*/:
                    value = value(i).getImaginary();
            }
            data.add(new Data(argument(i), value));
        }
        return data;
    }
}
