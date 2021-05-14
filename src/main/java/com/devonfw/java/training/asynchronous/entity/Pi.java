package com.devonfw.java.training.asynchronous.entity;

public class Pi {
    private Double computedPi;
    private Double error;

    public Pi(Double computedPi, Double error) {
        this.computedPi = computedPi;
        this.error = error;
    }

    public Double getError() {
        return error;
    }

    public Double getComputedPi() {
        return computedPi;
    }

}
