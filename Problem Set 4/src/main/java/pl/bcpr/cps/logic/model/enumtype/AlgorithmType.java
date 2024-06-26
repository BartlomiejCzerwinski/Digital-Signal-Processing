package pl.bcpr.cps.logic.model.enumtype;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AlgorithmType {
    BY_DEFINITION("Z definicji"),
    FAST_TRANSFORMATION("Szybka transformacja");

    private final String name;

    AlgorithmType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AlgorithmType fromString(final String text) {
        return Arrays.asList(AlgorithmType.values())
                .stream()
                .filter((it) -> it.getName().equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<String> getNamesList() {
        return Arrays.asList(AlgorithmType.values())
                .stream()
                .map((it) -> it.getName())
                .collect(Collectors.toList());
    }
}
