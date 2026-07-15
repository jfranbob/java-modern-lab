# Java Records — References

## Official Documentation

- [JEP 395: Records](https://openjdk.org/jeps/395) — OpenJDK, finalized in JDK 16
- [JEP 359: Records (Preview)](https://openjdk.java.net/jeps/359) — First preview in JDK 14
- [JEP 384: Records (Second Preview)](https://openjdk.java.net/jeps/384) — Second preview in JDK 15
- [Java Language Specification §8.10](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.10) — Record classes
- [Record Classes (Oracle)](https://docs.oracle.com/en/java/javase/21/language/records.html) — Java Language Updates guide
- [Serializable Records](https://docs.oracle.com/en/java/javase/21/serializable-records/index.html) — Chris Hegarty, Oracle
- [Java Object Serialization Specification §1.13](https://docs.oracle.com/en/java/javase/21/docs/specs/serialization/serial-arch.html#serialization-of-records) — Serialization of Records
- [java.lang.Record (JavaDoc)](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Record.html)
- [java.lang.reflect.RecordComponent (JavaDoc)](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/reflect/RecordComponent.html)

## Specifications

- [Jakarta Persistence 3.1 Specification](https://jakarta.ee/specifications/persistence/3.1/) — Entity requirements (no-arg constructor, non-final)
- [JavaBeans Specification](https://www.oracle.com/java/technologies/javase/javabeans-spec.html) — Naming conventions for accessors

## Books

- _Modern Java in Action_ by Raoul-Gabriel Urma, Mario Fusco, Alan Mycroft (Manning, 2024)
- _Java 17 in Action_ by Cay S. Horstmann (Manning, 2022)
- _Effective Java, 3rd Edition_ by Joshua Bloch (Addison-Wesley, 2018) — Item 17: Design and document for inheritance or else prohibit it

## Articles and Talks

- [Java 14 Feature Spotlight: Records](https://www.infoq.com/articles/java-14-feature-spotlight/) — Brian Goetz, InfoQ
- [Record Serialization — Sip of Java](https://inside.java/2021/10/21/sip24/) — Billy Korando, Inside.java
- [Records Come to Java](https://blogs.oracle.com/javamagazine/records-come-to-java) — Gavin Bierman, Java Magazine
- [Data-Oriented Programming in Java 21](https://www.infoq.com/articles/data-oriented-programming-java21/) — Brian Goetz
- [Records in Production: Where They Shine and Where They Silently Fail](https://www.javacodegeeks.com/2026/06/records-in-production-where-they-shine-and-where-they-silently-fail.html) — Java Code Geeks, 2026

## Benchmarks

- [The Hidden Cost of Records](https://www.javacodegeeks.com/2026/04/the-hidden-cost-of-records-when-java-records-break-your-serialization-jpa-and-reflection-heavy-code.html) — Java Code Geeks, 2026
- [Generational ZGC Benchmark Data](https://inside.java/2023/11/28/gen-zgc-explainer/) — Inside.java

## Related Projects

- [Project Lombok](https://projectlombok.org/) — Annotation-based code generation
- [AutoValue](https://github.com/google/auto/tree/main/value) — Google's value type generator
- [Immutables](https://immutables.github.io/) — Annotation-based immutable objects
- [JMH](https://github.com/openjdk/jmh) — Java Microbenchmark Harness
