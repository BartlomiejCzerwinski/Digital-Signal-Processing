package pl.bcpr.cps;

import pl.bcpr.cps.executionmode.graphical.GraphicalMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    private static List<String> mainArgs;

    public static List<String> getMainArgs() {
        return Collections.unmodifiableList(mainArgs);
    }

    public static void main(String[] args) {
        mainArgs = Arrays.asList(args);

        if (args.length == 0) {
            new GraphicalMode().main();
        }
    }
}

