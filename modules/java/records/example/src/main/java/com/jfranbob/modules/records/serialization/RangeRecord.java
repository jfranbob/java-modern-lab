package com.jfranbob.modules.records.serialization;

import java.io.Serial;
import java.io.Serializable;

public record RangeRecord(int lo, int hi) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public RangeRecord {
        if (lo > hi) {
            throw new IllegalArgumentException("lo (" + lo + ") must not be greater than hi (" + hi + ")");
        }
    }
}
