package com.jfranbob.benchmarks.records;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
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
public class HashMapBenchmark {

    private static final int MAP_SIZE = 1000;

    private Map<PointRecord, String> recordMap;
    private Map<PointHandCoded, String> handCodedMap;
    private PointRecord recordKey;
    private PointHandCoded handCodedKey;

    @Setup(Level.Trial)
    public void setup() {
        recordMap = new HashMap<>();
        handCodedMap = new HashMap<>();
        for (int i = 0; i < MAP_SIZE; i++) {
            recordMap.put(new PointRecord(i, i * 2), "value-" + i);
            handCodedMap.put(new PointHandCoded(i, i * 2), "value-" + i);
        }
        recordKey = new PointRecord(42, 84);
        handCodedKey = new PointHandCoded(42, 84);
    }

    @Benchmark
    public String recordHashMapGet() {
        return recordMap.get(recordKey);
    }

    @Benchmark
    public String handCodedHashMapGet() {
        return handCodedMap.get(handCodedKey);
    }

    @Benchmark
    public void recordHashMapPut(Blackhole bh) {
        var map = new HashMap<PointRecord, String>();
        for (int i = 0; i < 100; i++) {
            map.put(new PointRecord(i, i), "v");
        }
        bh.consume(map);
    }

    @Benchmark
    public void handCodedHashMapPut(Blackhole bh) {
        var map = new HashMap<PointHandCoded, String>();
        for (int i = 0; i < 100; i++) {
            map.put(new PointHandCoded(i, i), "v");
        }
        bh.consume(map);
    }
}
