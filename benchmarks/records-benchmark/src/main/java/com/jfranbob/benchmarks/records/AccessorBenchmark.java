package com.jfranbob.benchmarks.records;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(2)
@State(Scope.Thread)
public class AccessorBenchmark {

    private PointRecord record;
    private PointHandCoded handCoded;
    private PointLombokData lombokData;
    private PointLombokValue lombokValue;

    @Setup
    public void setup() {
        record = new PointRecord(10, 20);
        handCoded = new PointHandCoded(10, 20);
        lombokData = new PointLombokData(10, 20);
        lombokValue = new PointLombokValue(10, 20);
    }

    @Benchmark
    public void recordAccessor(Blackhole bh) {
        bh.consume(record.x());
        bh.consume(record.y());
    }

    @Benchmark
    public void handCodedAccessor(Blackhole bh) {
        bh.consume(handCoded.x());
        bh.consume(handCoded.y());
    }

    @Benchmark
    public void lombokDataGetter(Blackhole bh) {
        bh.consume(lombokData.getX());
        bh.consume(lombokData.getY());
    }

    @Benchmark
    public void lombokValueGetter(Blackhole bh) {
        bh.consume(lombokValue.getX());
        bh.consume(lombokValue.getY());
    }
}
