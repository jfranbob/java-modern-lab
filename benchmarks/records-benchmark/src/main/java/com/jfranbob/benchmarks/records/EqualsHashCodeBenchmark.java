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

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(2)
@State(Scope.Thread)
public class EqualsHashCodeBenchmark {

    private PointRecord recordA;
    private PointRecord recordB;
    private PointHandCoded handCodedA;
    private PointHandCoded handCodedB;
    private PointLombokData lombokDataA;
    private PointLombokData lombokDataB;
    private PointLombokValue lombokValueA;
    private PointLombokValue lombokValueB;

    @Setup
    public void setup() {
        recordA = new PointRecord(10, 20);
        recordB = new PointRecord(10, 20);
        handCodedA = new PointHandCoded(10, 20);
        handCodedB = new PointHandCoded(10, 20);
        lombokDataA = new PointLombokData(10, 20);
        lombokDataB = new PointLombokData(10, 20);
        lombokValueA = new PointLombokValue(10, 20);
        lombokValueB = new PointLombokValue(10, 20);
    }

    @Benchmark
    public boolean recordEquals() {
        return recordA.equals(recordB);
    }

    @Benchmark
    public boolean handCodedEquals() {
        return handCodedA.equals(handCodedB);
    }

    @Benchmark
    public boolean lombokDataEquals() {
        return lombokDataA.equals(lombokDataB);
    }

    @Benchmark
    public boolean lombokValueEquals() {
        return lombokValueA.equals(lombokValueB);
    }

    @Benchmark
    public int recordHashCode() {
        return recordA.hashCode();
    }

    @Benchmark
    public int handCodedHashCode() {
        return handCodedA.hashCode();
    }

    @Benchmark
    public int lombokDataHashCode() {
        return lombokDataA.hashCode();
    }

    @Benchmark
    public int lombokValueHashCode() {
        return lombokValueA.hashCode();
    }
}
