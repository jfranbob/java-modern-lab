---
name: create-module
description: Scaffold a complete engineering module under modules/. Use when asked to create, add, or scaffold a new module.
---

# Create Module

Use this when you need to create a new engineering module.

## Process

1. Determine the module name and domain (java, spring, architecture, dop, or ai).
2. Create `modules/<domain>/<module-name>/` with the standard structure.
3. Scaffold all required files.
4. Write the module content.
5. Register the module in `modules/README.md`.

## Standard Structure

```
modules/<domain>/<module-name>/
├── README.md         Engineering question and overview
├── theory.md         Deep explanation
├── example/          Runnable Java 25 code
│   └── Main.java
├── tests/            Automated tests
│   └── ModuleTest.java
├── decision.md       ADR-style decision record
└── references.md     Authoritative sources
```

## Java 25 Features to Consider

- Records
- Sealed classes and interfaces
- Pattern matching for switch
- Pattern matching for instanceof
- Virtual threads (Project Loom)
- Structured concurrency
- Scoped values
- String templates (if preview)
- Stream gatherers (if preview)
- Foreign Function & Memory API (if preview)
