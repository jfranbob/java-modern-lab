package com.jfranbob.modules.records.serialization;

import java.io.Serializable;

public record RangeRecord(int lo, int hi) implements Serializable {

    public RangeRecord {
        if (lo > hi) {
            throw new IllegalArgumentException("lo (" + lo + ") must not be greater than hi (" + hi + ")");
        }
    }
}
