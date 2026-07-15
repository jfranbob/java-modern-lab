package com.jfranbob.benchmarks.records;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PointLombokValue {

    private final int x;
    private final int y;
}
