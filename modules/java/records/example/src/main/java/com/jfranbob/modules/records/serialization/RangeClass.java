package com.jfranbob.modules.records.serialization;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings({"PMD.AvoidFieldNameMatchingMethodName", "PMD.ShortMethodName"})
public final class RangeClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int lo;
    private final int hi;

    public RangeClass(int lo, int hi) {
        if (lo > hi) {
            throw new IllegalArgumentException("lo (" + lo + ") must not be greater than hi (" + hi + ")");
        }
        this.lo = lo;
        this.hi = hi;
    }

    public int lo() {
        return lo;
    }

    public int hi() {
        return hi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RangeClass that)) {
            return false;
        }
        return lo == that.lo && hi == that.hi;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lo, hi);
    }

    @Override
    public String toString() {
        return "RangeClass[lo=" + lo + ", hi=" + hi + "]";
    }
}
