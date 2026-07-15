# Java Records

> **Engineering Question:** Should Java Records replace traditional DTO classes?

## Executive Summary

Java Records (JEP 395, finalized in JDK 16) are a language-native construct for
immutable data carriers. They eliminate boilerplate while providing stronger
guarantees around immutability, equality, and serialization safety than
traditional POJOs or Lombok.

After thorough investigation — specification analysis, hands-on examples,
integration testing with Spring/Jackson/JPA, and JMH benchmarks — the answer
is:

> Records should become the **default choice** for immutable data carriers,
> but they are **not a universal replacement** for all classes.

## Final Recommendation

| Scenario | Recommendation |
|---|---|
| REST DTO (request/response) | **Record** |
| JPA Entity / `@Entity` | **Class** (never Record) |
| JPA Projection (read-only) | **Record** |
| Domain Value Object | **Record** |
| Aggregate Root | **Class** |
| `@ConfigurationProperties` | **Record** |
| Mutable Service/Model class | **Class** |
| Simple immutable POJO | **Record** |
| Builder pattern | **Lombok or Record + extra** |
| FlatMap intermediate grouping | **Local Record** |

## Key Findings

1.  **Records are not just syntax sugar** — they enforce semantic constraints
    (immutability, transparent state, canonical construction) that prevent
    entire categories of bugs.
2.  **Serialization is safer** — deserialization goes through the canonical
    constructor, preventing "impossible objects". No `serialVersionUID` needed.
3.  **JPA entities are fundamentally incompatible** with records (no no-arg
    constructor, final class). Records work perfectly as read-only projections.
4.  **Performance is equivalent** to hand-coded classes in all practical
    scenarios. No overhead from the record construct itself.
5.  **Records are not a Lombok killer** — Lombok still handles mutability,
    builders, logging, and older Java versions. Use both where appropriate.
6.  **Decision framework** — see `decision.md` for the detailed engineering
    decision matrix and migration guide.

## Module Structure

```
records/
├── README.md              This file
├── theory.md              Deep explanation of Records
├── decision.md            Engineering Decision Record (findings, trade-offs, matrix, migration)
├── references.md          Official sources and further reading
├── example/src/main/java/ Runnable code
│   └── com/jfranbob/modules/records/
│       ├── dto/           REST DTO examples
│       ├── valueobjects/  Domain value objects (Email, Money, etc.)
│       ├── sealedhierarchies/ Algebraic data types with sealed interfaces
│       ├── spring/        Spring integration (@ConfigurationProperties)
│       ├── serialization/ Java serialization and Jackson
│       └── migration/     Migration path: Class → Lombok → Record
├── tests/src/test/java/   Automated tests
│   └── com/jfranbob/modules/records/
│       ├── unit/          Unit tests
│       └── integration/   Integration tests (Jackson, JPA simulation)
└── benchmark/             JMH benchmarks
```
