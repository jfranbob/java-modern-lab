# Modules

Each module explores a specific engineering topic.

Every module follows the same structure:

- README
- Theory
- Example
- Tests
- Decision Record
- References

Modules are grouped by domain rather than by difficulty.

The goal is to create a consistent and discoverable knowledge base.

## Creating a New Module

Use the template at `templates/module-template/` to bootstrap new modules.

The template includes placeholder files and a `pom.xml` configured to compile
from `example/` (main source) and `tests/` (test source) directories.

To create a new module:

1. Copy `templates/module-template/` to `modules/<domain>/<module-name>/`
2. Rename packages from `modulename` to your module name
3. Replace `MODULE_*` placeholders in all files
4. Implement the example code and tests
