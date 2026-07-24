package com.jfranbob.modules.records.serialization;

import java.io.Serial;
import java.io.Serializable;

public record PersonRecord(String name, int age) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
