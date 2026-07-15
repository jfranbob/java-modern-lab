package com.jfranbob.benchmarks.records;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(2)
@State(Scope.Thread)
public class CreationBenchmark {

    @Benchmark
    public void record(Blackhole bh) {
        bh.consume(new PointRecord(1, 2));
    }

    @Benchmark
    public void handCoded(Blackhole bh) {
        bh.consume(new PointHandCoded(1, 2));
    }

    @Benchmark
    public void lombokData(Blackhole bh) {
        bh.consume(new PointLombokData(1, 2));
    }

    @Benchmark
    public void lombokValue(Blackhole bh) {
        bh.consume(new PointLombokValue(1, 2));
    }

    @Benchmark
    public void recordString(Blackhole bh) {
        bh.consume(new PointRecord(42, 99));
    }

    @Benchmark
    public void handCodedString(Blackhole bh) {
        bh.consume(new PointHandCoded(42, 99));
    }
}
