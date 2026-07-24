package com.jfranbob.modules.records.migration;

import java.util.Locale;

public record AfterRecord(String value) {

    public AfterRecord {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("value must not be null or blank");
        }
    }

    public String toUpperCase() {
        return value.toUpperCase(Locale.ROOT);
    }
}
