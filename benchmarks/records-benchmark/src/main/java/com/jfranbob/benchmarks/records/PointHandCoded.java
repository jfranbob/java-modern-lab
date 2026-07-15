package com.jfranbob.benchmarks.records;

import java.util.Objects;

@SuppressWarnings({"PMD.AvoidFieldNameMatchingMethodName", "PMD.ShortMethodName"})
public final class PointHandCoded {

    private final int x;
    private final int y;

    public PointHandCoded(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointHandCoded that)) {
            return false;
        }
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "PointHandCoded[x=" + x + ", y=" + y + "]";
    }
}
