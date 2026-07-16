# Java Records

> **Engineering Question:** Should Java Records replace traditional DTO classes? Engineering analysis with benchmarks, examples, and decision framework.

## Overview

This module provides a comprehensive analysis of Java Records (JEP 395) as a replacement for traditional DTO/POJO classes. It covers DTOs, value objects with invariants, sealed hierarchies, Spring integration, serialization behavior, migration patterns, and includes 80+ tests with JMH benchmarks.

## What You Will Learn

- When to use records vs traditional classes for DTOs
- How to implement value objects with validation in records
- Sealed interfaces with record implementations for algebraic data types
- Spring Framework integration with records
- Serialization behavior differences (equals, hashCode, toString, serialization)
- Step-by-step migration from traditional classes to records
- Performance characteristics via JMH benchmarks

## Prerequisites

- JDK 21+
- Familiarity with Java records, sealed classes, and pattern matching
- Basic understanding of DTO patterns and serialization

## Project Structure

```
records/
├── README.md           This file
├── theory.md           Deep explanation of records
├── decision.md         Engineering Decision Record
├── references.md       Sources and further reading
├── ai-review.md        AI-assisted review notes
├── example/
│   └── src/main/java/  Runnable examples
└── tests/
    └── src/test/java/  80+ automated tests
```