package pl.bcpr.cps.logic.model;

import java.io.Serializable;

@FunctionalInterface
public interface Operation extends Serializable {
    double operation(double a, double b);
}
