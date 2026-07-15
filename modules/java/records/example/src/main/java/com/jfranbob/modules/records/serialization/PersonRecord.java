package com.jfranbob.modules.records.serialization;

import java.io.Serializable;

public record PersonRecord(String name, int age) implements Serializable {}
