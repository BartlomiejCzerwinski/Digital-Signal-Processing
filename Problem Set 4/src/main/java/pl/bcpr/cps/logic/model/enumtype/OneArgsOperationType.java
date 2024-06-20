package pl.bcpr.cps.logic.model.enumtype;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum OneArgsOperationType {

    SAMPLING("Próbkowanie"),
    DISCRETE_FOURIER_TRANSFORMATION("Dyskretna transformacja Fouriera"),
    COSINE_TRANSFORMATION("Transformacja kosinusowa"),
    WALSH_HADAMARD_TRANSFORMATION("Transformacja Walsha-Hadamarda"),
    WAVELET_TRANSFORMATION("Transformacja falkowa");

    private final String name;

    OneArgsOperationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static OneArgsOperationType fromString(final String text) {
        return Arrays.asList(OneArgsOperationType.values())
                .stream()
                .filter((it) -> it.getName().equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<String> getNamesList() {
        return Arrays.asList(OneArgsOperationType.values())
                .stream()
                .map((it) -> it.getName())
                .collect(Collectors.toList());
    }
}
    