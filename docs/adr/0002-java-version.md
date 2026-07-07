# ADR-0002: Always the Latest Stable JDK

## Status

Accepted (supersedes previous version)

## Context

Java Modern Lab aims to explore and evaluate modern Java through engineering experiments.

Targeting a fixed LTS version would limit the ability to test new JEPs, language features, and JVM improvements as they become available.

## Decision

Always adopt the latest stable JDK as the primary development target.

The project evolves together with the Java platform. When a new JDK is released, the project migrates and documents the changes.

Current version: **JDK 26**

## Consequences

- Early adoption of new language features and APIs.
- Better understanding of upcoming standards.
- Some examples may require recent JDKs.
- Production compatibility is not the primary goal.
- Each new JDK release generates research issues and new modules.
