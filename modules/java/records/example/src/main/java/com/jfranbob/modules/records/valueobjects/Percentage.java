package com.jfranbob.modules.records.valueobjects;

public record Percentage(double value) {

    public Percentage {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100, got: " + value);
        }
    }

    public double asFraction() {
        return value / 100.0;
    }

    public Percentage add(Percentage other) {
        return new Percentage(this.value + other.value);
    }

    @SuppressWarnings("PMD.ShortMethodName")
    public static Percentage of(double value) {
        return new Percentage(value);
    }
}
