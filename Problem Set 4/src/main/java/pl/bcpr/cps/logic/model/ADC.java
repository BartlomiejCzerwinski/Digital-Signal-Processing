package pl.bcpr.cps.logic.model;

import pl.bcpr.cps.logic.model.signal.ContinuousBasedDiscreteSignal;
import pl.bcpr.cps.logic.model.signal.ContinuousSignal;
import pl.bcpr.cps.logic.model.signal.DiscreteSignal;

public class ADC {

    public DiscreteSignal sampling(ContinuousSignal signal, double sampleRate) {
        return new ContinuousBasedDiscreteSignal(signal.getRangeStart(),
                signal.getRangeLength(), sampleRate, signal);
    }

}
