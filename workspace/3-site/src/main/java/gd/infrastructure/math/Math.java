package gd.infrastructure.math;

import gd.infrastructure.steriotype.GDService;

@GDService
public class Math {

    // fitting
    public double fitBetweenMinAndMax(double weight, double min, double max) {
        min = min + 0.1f;
        double normalizedWeight = (weight - min) / (max - min);
        return java.lang.Math.abs(normalizedWeight);
    }

}
