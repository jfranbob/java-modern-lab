---
name: benchmark
description: Set up JMH benchmarks under benchmarks/. Use when asked to create or run a benchmark.
---

# Benchmark

Use this when you need to create or configure a JMH benchmark.

## Process

1. Create the benchmark under `benchmarks/<benchmark-name>/`.
2. Include a pom.xml or add a module entry to the root pom.xml.
3. Add JMH dependency.
4. Write the benchmark class.
5. Add a README explaining the benchmark question and methodology.

## Standard Structure

```
benchmarks/<benchmark-name>/
├── pom.xml            JMH-specific dependencies
├── src/main/java/     Benchmark code
│   └── BenchmarkName.java
├── src/main/resources/
│   └── jmh.properties
└── README.md          Methodology and results
```

## Key Dependencies

- JMH Core
- JMH Annotation Processor
- JUnit (for verification)
