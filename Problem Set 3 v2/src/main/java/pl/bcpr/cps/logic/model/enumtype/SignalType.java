package pl.bcpr.cps.logic.model.enumtype;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SignalType {

    SINUSOIDAL_SIGNAL("Sygnał sinusoidalny"),
    SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL("Sygnał sinusoidalny wyprostowany jednopołówkowo"),
    SINUSOIDAL_RECTIFIED_IN_TWO_HALVES("Sygnał sinusoidalny wyprostowany dwupołówkowo"),
    RECTANGULAR_SIGNAL("Sygnał prostokątny"),
    SYMMETRICAL_RECTANGULAR_SIGNAL("Sygnał prostokątny symetryczny"),
    TRIANGULAR_SIGNAL("Sygnał trójkątny"),
    LOW_PASS_FILTER("Filtr dolnoprzepustowy"),
    BAND_PASS_FILTER("Filtr pasmowy"),
    HIGH_PASS_FILTER("Filtr górnoprzepustowy");

    private final String name;

    SignalType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SignalType fromString(final String text) {
        return Arrays.asList(SignalType.values())
                .stream()
                .filter((it) -> it.getName().equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<String> getNamesList() {
        return Arrays.asList(SignalType.values())
                .stream()
                .map((it) -> it.getName())
                .collect(Collectors.toList());
    }
}
