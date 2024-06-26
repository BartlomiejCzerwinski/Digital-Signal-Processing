package pl.bcpr.cps.logic.model.enumtype;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TwoArgsOperationType {

    ADDITION("Dodawanie"),
    SUBTRACTION("Odejmowanie"),
    MULTIPLICATION("Mnożenie"),
    DIVISION("Dzielenie"),
    CONVOLUTION("Splot"),
    CORRELATION("Korelacja");

    private final String name;

    TwoArgsOperationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TwoArgsOperationType fromString(final String text) {
        return Arrays.asList(TwoArgsOperationType.values())
                .stream()
                .filter((it) -> it.getName().equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<String> getNamesList() {
        return Arrays.asList(TwoArgsOperationType.values())
                .stream()
                .map((it) -> it.getName())
                .collect(Collectors.toList());
    }
}
