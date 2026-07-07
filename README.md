# Java Modern Lab

> **A living open-source laboratory for modern Java, software architecture, and AI-assisted engineering.**

Java Modern Lab is an open-source project that explores modern Java and software engineering through practical experiments, reproducible benchmarks, architectural decisions, and real-world examples.

This is **not** another collection of code snippets or CRUD tutorials.

Its goal is to answer engineering questions by combining experimentation, documentation, and measurable results.

Every module is built around a simple principle:

> **Understand first. Measure second. Share forever.**

---

# Vision

Software development is changing rapidly.

Modern Java continues to evolve with new language features, the JVM keeps improving, cloud-native architectures become more sophisticated, and AI is transforming how software is designed and developed.

Instead of treating these topics as isolated technologies, Java Modern Lab studies how they work together.

The project aims to become a long-term knowledge base for developers who want to understand **why** a solution exists, **when** it should be used, and **what trade-offs** it introduces.

This repository is intentionally designed as a living laboratory that grows together with the Java ecosystem.

---

# Mission

Build one of the most comprehensive open-source references for modern Java engineering by providing:

* High-quality code examples
* Architectural experiments
* Performance benchmarks
* Engineering decision records
* AI-assisted development workflows
* Production-oriented best practices

The objective is not to teach Java syntax.

The objective is to teach engineering.

---

# Core Principles

Every contribution must follow these principles.

## Learn by Experimenting

Every concept should be validated with code whenever possible.

Theory without experimentation has limited value.

---

## Explain the Why

Every module must answer a real engineering question.

Examples:

* Why should I use Virtual Threads?
* When should I prefer Records over traditional classes?
* Is Data-Oriented Programming always a good idea?
* When does Hexagonal Architecture become unnecessary?

The explanation is more important than the implementation.

---

## Keep Everything Reproducible

Examples should compile.

Benchmarks should be reproducible.

Documentation should be versioned.

Results should be verifiable.

---

## Prefer Official Sources

Whenever possible, modules should reference:

* JEPs
* Official Java documentation
* Spring documentation
* Academic papers
* Conference talks
* Books from recognized authors

---

## Quality Over Quantity

Ten excellent modules are more valuable than one hundred incomplete examples.

Every published module should be something you would proudly present during a technical interview.

---

# What This Project Is

Java Modern Lab is:

* A learning platform
* A software engineering laboratory
* A modern Java knowledge base
* A collection of reproducible experiments
* A reference implementation for best practices
* A long-term engineering portfolio

---

# What This Project Is Not

This repository is NOT:

* A Java course
* A Spring Boot tutorial
* A CRUD application
* A design pattern catalog
* A LeetCode repository
* A framework showcase

Many excellent resources already exist for those purposes.

This project focuses on engineering decisions rather than API documentation.

---

# Project Structure

```text
java-modern-lab/
│
├── .github/
│   ├── workflows/              CI/CD pipeline configuration
│   ├── ISSUE_TEMPLATE/         Issue forms (Research, Feature, Benchmark, Docs, Bug, Engineering Question)
│   ├── PULL_REQUEST_TEMPLATE.md
│   ├── CODEOWNERS
│   └── dependabot.yml
│
├── docs/
│   ├── adr/                    Architecture Decision Records
│   ├── vision.md               Project vision
│   ├── roadmap.md              Version roadmap
│   ├── coding-standards.md     Code quality and formatting rules
│   ├── quality-gates.md        Merge requirements
│   ├── architecture.md         Repository architecture overview
│   ├── dev-log.md              Development diary
│   └── references.md           Curated reference links
│
├── modules/
│   ├── java/                   Modern Java language features and JVM capabilities
│   ├── spring/                 Spring Boot, Spring Framework and production-ready practices
│   ├── architecture/           Software architecture patterns and design approaches
│   ├── dop/                    Data-Oriented Programming experiments
│   ├── ai/                     AI-assisted software engineering workflows
│   └── README.md               Module structure guide
│
├── benchmarks/
│       JMH benchmarks and performance analysis
│
├── templates/
│   ├── module-template/        Reusable template for bootstrapping new modules
│   └── benchmark-template/     Reusable template for benchmarks
│
├── ENGINEERING_PRINCIPLES.md   10 guiding principles for the project
├── CHANGELOG.md
├── SECURITY.md
├── pom.xml                     Maven project (JDK 26)
├── .editorconfig
└── .gitignore
```

---

# Module Structure

Every module follows the same structure.

```text
module/

README.md

theory.md

example/

tests/

benchmark/

decision.md

references.md

ai-review.md
```

This consistency makes the repository predictable and easy to navigate.

---

# Supported JDK

Java Modern Lab targets the latest stable JDK.

The project evolves together with the Java platform to explore new language features, APIs and JVM improvements.

**Current version:** JDK 26

Preview features are only enabled in specific modules that require them, and are always documented.

When a new JDK is released, a research issue is created to evaluate migration, document new JEPs, and assess impact.

---

# Building the Project

## Prerequisites

- **JDK 26** (latest stable)
- No Maven installation required (wrapper included)

## Commands

```bash
# Build the project
./mvnw compile

# Run tests
./mvnw test

# Check code formatting
./mvnw spotless:check

# Auto-fix formatting
./mvnw spotless:apply
```

All commands use the Maven Wrapper (`./mvnw`) to ensure reproducible builds.

---
# Engineering Workflow

Every module follows the same lifecycle.

1. Identify an engineering question.
2. Research official references.
3. Design a minimal implementation.
4. Write automated tests.
5. Measure performance when applicable.
6. Document engineering decisions.
7. Evaluate AI-generated solutions.
8. Publish the results.

---

# Quality Standards

Every module must:

* Build successfully
* Include automated tests
* Follow the project's coding standards
* Be fully documented
* Reference authoritative sources
* Include engineering decisions
* Explain trade-offs
* Pass the CI pipeline

No exceptions.

---

# Roadmap

## Phase 1 — Project Foundation

* Repository structure
* Documentation
* CI/CD
* Coding standards
* Quality gates

---

## Phase 2 — Modern Java

* Records
* Sealed Classes
* Pattern Matching
* Virtual Threads
* Structured Concurrency
* Scoped Values
* Stream Gatherers
* Foreign Function & Memory API
* Vector API

---

## Phase 3 — Data-Oriented Programming

* Immutable models
* Functional transformations
* Data pipelines
* Algebraic data types
* Practical DOP patterns

---

## Phase 4 — Spring Ecosystem

* Spring Boot
* Native Images
* Observability
* Security
* REST Clients
* Testing

---

## Phase 5 — Software Architecture

* Hexagonal Architecture
* Modular Monolith
* Domain-Driven Design
* CQRS
* Event Sourcing
* Outbox Pattern
* Saga Pattern

---

## Phase 6 — AI-Assisted Development

* OpenCode workflows
* AI code reviews
* Prompt engineering
* Model Context Protocol (MCP)
* Autonomous development agents

---

## Phase 7 — Performance Engineering

* JMH benchmarks
* JVM tuning
* Memory analysis
* Concurrency experiments

---

# Who Is This For?

This project is intended for:

* Java developers
* Software engineers
* Technical leads
* Software architects
* Students who already know Java fundamentals
* Anyone interested in modern software engineering

---

# Long-Term Goal

Java Modern Lab is intended to grow alongside the Java ecosystem.

Rather than becoming "finished", it will continuously evolve as new JDK releases, JEPs, architectural approaches, and AI capabilities emerge.

The ultimate objective is to build a trusted engineering reference that helps developers make better technical decisions.

---

# Contributing

Contributions are welcome.

Before opening a Pull Request, please read the contribution guide and ensure that every quality requirement is satisfied.

Engineering excellence is valued over implementation speed.

---

# License

This project is released under the MIT License.

Feel free to learn, contribute, experiment, and build upon it.
