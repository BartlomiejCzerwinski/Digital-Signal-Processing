package pl.bcpr.cps.logic.model.signal;

import java.util.List;

import pl.bcpr.cps.logic.model.Data;

public abstract class ContinuousSignal extends Signal {

    public ContinuousSignal(double rangeStart, double rangeLength) {
        super(rangeStart, rangeLength);
    }

    public abstract double value(double t);

    @Override
    public List<Data> generateDiscreteRepresentation() {
        return new ContinuousBasedDiscreteSignal(getRangeStart(), getRangeLength(),
                1000000.0 / getRangeLength(), this)
                .generateDiscreteRepresentation();
    }
}
