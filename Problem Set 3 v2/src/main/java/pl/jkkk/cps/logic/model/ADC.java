package pl.jkkk.cps.logic.model;

import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.ContinuousBasedDiscreteSignal;
import pl.jkkk.cps.logic.model.signal.DiscreteSignal;

public class ADC {

    public DiscreteSignal sampling(ContinuousSignal signal, double sampleRate) {
        return new ContinuousBasedDiscreteSignal(signal.getRangeStart(),
                signal.getRangeLength(), sampleRate, signal);
    }

}
