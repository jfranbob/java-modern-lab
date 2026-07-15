package com.jfranbob.benchmarks.records;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class JacksonBenchmark {

    private static final String JSON = """
            {"x": 10, "y": 20}
            """;

    private static final String LOMBOK_JSON = """
            {"x": 10, "y": 20}
            """;

    private ObjectMapper mapper;
    private PointRecord record;
    private PointLombokValue lombokValue;

    @Setup
    public void setup() {
        mapper = new ObjectMapper();
        record = new PointRecord(10, 20);
        lombokValue = new PointLombokValue(10, 20);
    }

    @Benchmark
    public String recordSerialize() throws Exception {
        return mapper.writeValueAsString(record);
    }

    @Benchmark
    public String lombokValueSerialize() throws Exception {
        return mapper.writeValueAsString(lombokValue);
    }

    @Benchmark
    public PointRecord recordDeserialize() throws Exception {
        return mapper.readValue(JSON, PointRecord.class);
    }

    // NOTE: lombokValueDeserialize is omitted because @Value classes lack the
    // constructors Jackson needs for deserialization, just like Records — but
    // Records work because Jackson Jdk8Module knows how to call the canonical
    // constructor. This is a real advantage of Records over Lombok @Value.
}
