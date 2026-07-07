# Java Modern Lab — Agent Context

## Identity

Java Modern Lab is an open-source engineering laboratory for modern Java, software architecture, and AI-assisted development. It is NOT a tutorial or CRUD collection — every module answers a real engineering question.

**Tagline:** Understand first. Measure second. Share forever.

**Principles:**
1. Understand before implementing.
2. Measure before optimizing.
3. Simplicity beats cleverness.
4. Every module answers a question.
5. Prefer official sources.
6. Favor immutability where appropriate.
7. Document decisions, not only code.
8. AI assists; engineers decide.
9. Quality is non-negotiable.
10. Learning is a continuous process.

---

## Project Structure

```
java-modern-lab/
├── .github/
│   ├── workflows/              CI/CD pipeline
│   ├── ISSUE_TEMPLATE/         Issue forms (research, feature, benchmark, docs, bug, engineering-question)
│   ├── PULL_REQUEST_TEMPLATE.md
│   ├── CODEOWNERS              * @jfranbob
│   └── dependabot.yml          Weekly Maven + GitHub Actions updates
├── docs/
│   ├── adr/                    Architecture Decision Records
│   ├── vision.md               Long-term project vision
│   ├── roadmap.md              Version roadmap (v0.1 → v1.0)
│   ├── coding-standards.md     Readability, formatting, naming
│   ├── quality-gates.md        Merge requirements
│   ├── architecture.md         Repository architecture with Mermaid diagram
│   ├── dev-log.md              Development diary
│   └── references.md           Curated links
├── modules/
│   ├── java/                   Modern Java features
│   ├── spring/                 Spring ecosystem
│   ├── architecture/           Architecture patterns
│   ├── dop/                    Data-Oriented Programming
│   ├── ai/                     AI-assisted workflows
│   └── README.md               Module structure guide
├── benchmarks/                 JMH benchmarks
├── templates/
│   ├── module-template/        Scaffold for new modules
│   └── benchmark-template/     Scaffold for benchmarks
├── opencode.json               OpenCode configuration
├── AGENTS.md                   This file
├── ENGINEERING_PRINCIPLES.md   10 guiding principles
├── CHANGELOG.md
├── SECURITY.md
├── pom.xml                     Maven, JDK 26
├── .editorconfig
└── .gitignore
```

---

## Module Structure

Every module in `modules/` follows:

```
module/
├── README.md         What and why
├── theory.md         Deep explanation
├── example/          Runnable code
├── tests/            Automated tests
├── decision.md       Engineering Decision Record
└── references.md     Sources and further reading
```

---

## Conventions

### Git

- **Branch naming:** `type/short-description` (e.g., `chore/add-config`, `feature/records-module`, `research/virtual-threads`)
- **Commit messages:** [Conventional Commits](https://www.conventionalcommits.org/) — `type: description` (e.g., `feat: add Records module`, `chore: update Maven config`, `docs: fix typo in README`)
- **PRs:** Use PULL_REQUEST_TEMPLATE.md — include Summary, Motivation, Changes, Tests, References, Checklist

### Types

- `feat` — new module or feature
- `fix` — bug fix
- `docs` — documentation only
- `chore` — build, CI, tooling, config
- `refactor` — code restructuring
- `test` — adding or fixing tests
- `bench` — benchmarks

### Labels

Issues and PRs use four label categories:

| Category | Values |
|---|---|
| `type:` | research, feature, benchmark, docs, bug, refactor, build, test |
| `area:` | java, spring, architecture, dop, ai, infrastructure, documentation, benchmarking |
| `priority:` | critical, high, medium, low |
| `status:` | blocked, needs-discussion, help-wanted, good-first-issue |

### Milestones

- v0.1 Foundation
- v0.2 Records
- v0.3 Sealed Classes
- v0.4 Pattern Matching
- v0.5 Virtual Threads
- v1.0 Public Release

---

## Engineering Workflow

1. **Engineering Question** — starts as an issue using `engineering-question.yml`
2. **Research** — investigate using `research.yml` template
3. **Implementation** — create the module
4. **Benchmark** — measure performance (optional)
5. **Documentation** — write docs and ADR
6. **Module Review** — quality gates must pass
7. **Release** — tag and publish

---

## Quality Gates

Every contribution must:

- Compile successfully (`mvn compile`)
- Pass formatting checks (`mvn spotless:check`)
- Include tests
- Be documented (README, theory, references)
- Include a Decision Record
- Reference authoritative sources
- Pass CI

---

## Java Configuration

- **Version:** JDK 26 (latest stable)
- **Build:** Maven (pom.xml at root)
- **Formatting:** Spotless (enforced)
- **Testing:** JUnit

---

## GitHub Project

The project board is at https://github.com/users/jfranbob/projects/3

Status columns: Ideas → Research → Ready → In Progress → Review → Done

Custom fields: Priority, Area, Release, Effort (1-5)
