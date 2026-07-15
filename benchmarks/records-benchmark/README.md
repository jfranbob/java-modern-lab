# Records Benchmark

> Compare performance of Java Records, hand-coded POJOs, and Lombok DTOs.

## Engineering Question

Is there a measurable performance difference between Records, hand-coded
classes, and Lombok-annotated classes for common operations?

## Hypothesis

Records compile to the same bytecode patterns as hand-coded classes.
There should be no statistically significant difference in:
- Object creation
- Accessor invocation
- `equals`/`hashCode`
- `toString`
- HashMap operations
- Jackson serialization/deserialization

## Methodology

Each benchmark measures **average time per operation** using JMH with:
- 5 warmup iterations (1 second each)
- 5 measurement iterations (1 second each)
- 2 forks
- Blackhole consumption to prevent dead-code elimination

### Benchmark Suites

| Class | Description |
|---|---|
| `CreationBenchmark` | Object allocation and construction |
| `AccessorBenchmark` | Field accessor/getter invocation |
| `EqualsHashCodeBenchmark` | `equals()` and `hashCode()` calls |
| `ToStringBenchmark` | `toString()` string construction |
| `HashMapBenchmark` | HashMap `get` (1000 entries) and `put` (100 entries) |
| `JacksonBenchmark` | Jackson JSON serialize + deserialize |

### Compared Types

| Type | Description |
|---|---|
| `PointRecord` | `record PointRecord(int x, int y)` |
| `PointHandCoded` | Hand-written final class, `Objects.hash`, manual equals |
| `PointLombokData` | `@Data @AllArgsConstructor` (mutable) |
| `PointLombokValue` | `@Value @AllArgsConstructor` (immutable) |

## Running

```bash
# Build the benchmark JAR
mvn clean package -pl benchmarks/records-benchmark -am

# Run all benchmarks
java -jar benchmarks/records-benchmark/target/benchmarks.jar

# Run specific benchmark
java -jar benchmarks/records-benchmark/target/benchmarks.jar CreationBenchmark

# Run with profiler (allocation)
java -jar benchmarks/records-benchmark/target/benchmarks.jar -prof gc

# Run with specific parameters
java -jar benchmarks/records-benchmark/target/benchmarks.jar \
  -wi 10 -i 10 -f 3
```

## Results

Results depend on JDK version, hardware, and GC configuration.

### JDK 25 (OpenJDK, Homebrew, 2025-09-16)

Hardware: Linux, AMD64. Benchmarks run with `-wi 5 -i 5 -f 1`.

| Benchmark | Mode | Score | Error | Units |
|---|---|---|---|---|
| CreationBenchmark.record | avgt | 3.945 | ±0.321 | ns/op |
| CreationBenchmark.handCoded | avgt | 3.984 | ±0.638 | ns/op |
| CreationBenchmark.recordString | avgt | 3.977 | ±0.254 | ns/op |
| CreationBenchmark.handCodedString | avgt | 4.161 | ±1.911 | ns/op |
| CreationBenchmark.lombokData | avgt | 3.915 | ±0.408 | ns/op |
| CreationBenchmark.lombokValue | avgt | 3.875 | ±0.322 | ns/op |
| AccessorBenchmark.recordAccessor | avgt | 1.350 | ±0.572 | ns/op |
| AccessorBenchmark.handCodedAccessor | avgt | 1.265 | ±0.094 | ns/op |
| AccessorBenchmark.lombokDataGetter | avgt | 1.211 | ±0.291 | ns/op |
| AccessorBenchmark.lombokValueGetter | avgt | 1.134 | ±0.056 | ns/op |
| EqualsHashCodeBenchmark.recordEquals | avgt | 1.853 | ±0.372 | ns/op |
| EqualsHashCodeBenchmark.recordHashCode | avgt | 1.244 | ±0.247 | ns/op |
| EqualsHashCodeBenchmark.handCodedEquals | avgt | 1.923 | ±0.159 | ns/op |
| EqualsHashCodeBenchmark.handCodedHashCode | avgt | 18.364 | ±8.586 | ns/op |
| EqualsHashCodeBenchmark.lombokDataEquals | avgt | 1.947 | ±0.149 | ns/op |
| EqualsHashCodeBenchmark.lombokDataHashCode | avgt | 1.372 | ±0.332 | ns/op |
| EqualsHashCodeBenchmark.lombokValueEquals | avgt | 1.946 | ±0.402 | ns/op |
| EqualsHashCodeBenchmark.lombokValueHashCode | avgt | 1.368 | ±0.238 | ns/op |
| ToStringBenchmark.recordToString | avgt | 23.923 | ±2.462 | ns/op |
| ToStringBenchmark.handCodedToString | avgt | 25.258 | ±5.916 | ns/op |
| ToStringBenchmark.lombokDataToString | avgt | 25.905 | ±4.931 | ns/op |
| ToStringBenchmark.lombokValueToString | avgt | 24.986 | ±9.446 | ns/op |
| HashMapBenchmark.recordHashMapGet | avgt | 5.724 | ±0.186 | ns/op |
| HashMapBenchmark.recordHashMapPut | avgt | 10746.313 | ±3922.456 | ns/op |
| HashMapBenchmark.handCodedHashMapGet | avgt | 29.828 | ±16.480 | ns/op |
| HashMapBenchmark.handCodedHashMapPut | avgt | 13301.494 | ±2765.003 | ns/op |
| JacksonBenchmark.recordSerialize | avgt | 343.082 | ±101.845 | ns/op |
| JacksonBenchmark.recordDeserialize | avgt | 563.939 | ±207.577 | ns/op |
| JacksonBenchmark.lombokValueSerialize | avgt | 324.695 | ±70.757 | ns/op |

## Observations

### Confirmed: No Performance Penalty

Records are equivalent to hand-coded classes and Lombok-annotated classes for
**creation**, **accessors**, **equals**, **toString**, and **Jackson
serialization** — all within measurement error.

### hashCode: Hand-Coded Classes Can Be Slower

The `handCodedHashCode` benchmark uses `Objects.hash(x, y)`, which allocates an
`Object[]` and boxes the primitives: **18.4 ns/op** vs **~1.3 ns/op** for
Records and Lombok. This is not a flaw in hand-coded classes per se — you can
write the same efficient hash as Records use — but Records *force* the
efficient implementation.

### HashMap Get: Records Are Faster

`recordHashMapGet` (5.7 ns/op) is ~5× faster than `handCodedHashMapGet` (29.8
ns/op). This is a direct consequence of the faster `hashCode`: fewer hash
collisions lead to fewer `equals` calls during map lookups.

### Jackson Deserialization: Only Records Work

`lombokValueDeserialize` failed because `@Value` classes lack the constructors
Jackson needs for deserialization. Records work because Jackson's `Jdk8Module`
recognises the canonical constructor. This is a real advantage of Records over
Lombok `@Value` for REST APIs.

### Serialization: Equivalent

Record and Lombok `@Value` serialize at the same speed (~330 ns/op).
