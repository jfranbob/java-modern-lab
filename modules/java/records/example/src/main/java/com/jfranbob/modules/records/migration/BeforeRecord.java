package com.jfranbob.modules.records.migration;

import java.util.Objects;

public final class BeforeRecord {

    private final String value;

    public BeforeRecord(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("value must not be null or blank");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    @SuppressWarnings("PMD.SimplifyBooleanReturns")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BeforeRecord that)) {
            return false;
        }
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "BeforeRecord{value='" + value + "'}";
    }
}
