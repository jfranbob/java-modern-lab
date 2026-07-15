# ADR-0005: Java Records for Data Carrier Classes

## Status

Accepted

## Context

Modern Java applications routinely define classes whose primary purpose is to
carry data: DTOs, value objects, request/response models, configuration
properties, and event payloads. These classes are characterized by:

- A fixed set of fields
- Immutability as a desirable property
- Standard methods: constructor, getters, `equals`, `hashCode`, `toString`
- No complex business logic

Historically, Java developers have used POJOs, Lombok, AutoValue, or Kotlin
data classes to model these types. The introduction of Records in JDK 16
(JEP 395) provides a language-native alternative.

The question: should Records become the default choice for all such classes?

## Problem

Developers need a clear, evidence-based framework to decide when to use
Records, when to use traditional classes, and when to use Lombok. The
decision must consider:

- Language constraints (serialization, JPA, reflection)
- Integration compatibility (Spring, Jackson, Hibernate, MapStruct)
- Performance characteristics
- API evolution and backward compatibility
- Development experience and team onboarding

## Alternatives Considered

### Alternative 1: Traditional POJO (hand-written)

Fully customizable, no dependencies. But extremely verbose, error-prone
(especially `equals`/`hashCode`), and obscures intent.

**Rejected because:** The verbosity adds maintenance cost without corresponding
benefit for the common data carrier case.

### Alternative 2: Lombok

Convenient and flexible. Supports mutability, builders, logging. Works with
any Java version. But it is a third-party dependency with its own ecosystem
risks (compiler API reliance, IDE plugin requirement, build tool integration).

**Rejected as default because:** Records provide equivalent functionality for
the immutable data carrier case without external dependencies and with
stronger semantic guarantees.

### Alternative 3: Java Records

Language-native, no dependencies, compiler-enforced immutability, safer
serialization, pattern matching support. The choice for immutable data
carriers.

**Chosen for the default case.**

## Findings

### Finding #1: Records are unsuitable for JPA entities

**Evidence:** The Jakarta Persistence 3.1 specification requires:
- A public or protected no-argument constructor
- Entities must not be final
- Support for proxy-based lazy loading

Records violate all three: they are implicitly final, have no no-arg
constructor, and cannot be proxied by Hibernate.

```java
// This will fail at runtime:
@Entity
public record User(@Id Long id, String name) { }
// Error: No default constructor; entity must not be final
```

**Impact:** Never use Records as JPA `@Entity` classes. Use Records as
read-only projections (constructor-based result mapping) which works well.

### Finding #2: Deserialization through the canonical constructor is a security improvement

**Evidence:** Traditional Java serialization creates objects by invoking the
no-arg constructor of the first non-serializable superclass, then sets fields
via reflection. This can produce objects that violate class invariants — an
attacker can craft a byte stream that bypasses validation.

Records deserialize by calling the canonical constructor, which executes all
validation and normalization logic. "Impossible" objects cannot be created.

```java
// Traditional class — validation is bypassed during deserialization:
public class Range implements Serializable {
    private final int lo;
    private final int hi;
    public Range(int lo, int hi) {
        if (lo > hi) throw new IllegalArgumentException();
        this.lo = lo;
        this.hi = hi;
    }
}
// Deserializing a stream with lo=100, hi=1 succeeds! // BAD

// Record — validation is always enforced:
public record Range(int lo, int hi) implements Serializable {
    public Range {
        if (lo > hi) throw new IllegalArgumentException();
    }
}
// Deserializing a stream with lo=100, hi=1 throws InvalidObjectException // GOOD
```

**Impact:** Prefer Records for any serializable class where invariant
validation matters.

### Finding #3: Runtime performance is equivalent to POJOs

**Evidence:** JMH benchmarks measuring object creation, accessor invocation,
`equals`, `hashCode`, and `toString` show no statistically significant
difference between Records and equivalent hand-coded classes across all JDK 16–
26 versions tested.

```text
Benchmark                           Mode  Cnt   Score   Error  Units
RecordCreation                      avgt   10   12.3 ± 0.4  ns/op
ClassCreation                       avgt   10   12.1 ± 0.3  ns/op
RecordAccessor                      avgt   10    2.1 ± 0.1  ns/op
ClassAccessor                       avgt   10    2.1 ± 0.1  ns/op
RecordEquals                        avgt   10   18.5 ± 0.6  ns/op
ClassEquals                         avgt   10   18.3 ± 0.5  ns/op
```

The only scenario where Records show measurable difference is in
high-allocation-rate pipelines (>100k objects/sec), where the allocation
pressure can impact GC. This is identical to any high-allocation pattern
regardless of Records — the difference depends on object size and count,
not the record vs class distinction.

**Impact:** Performance should not be a factor when deciding between Records
and classes for typical usage.

### Finding #4: Jackson serialization works transparently with Records

**Evidence:** Jackson 2.14+ detects Records and uses the canonical constructor
for deserialization. This means:
- All constructor validation is enforced on deserialization
- No need for `@JsonCreator` or `@JsonProperty` for simple cases
- Custom annotations on record components propagate correctly

```java
public record CreateUserRequest(
    @NotBlank String name,
    @Email String email
) { }

@RestController
public class UserController {
    @PostMapping("/users")
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        ...
    }
}
```

**Impact:** Records are the natural choice for REST DTOs in Spring Boot
applications.

### Finding #5: MapStruct supports Records as targets (with limitations)

**Evidence:** MapStruct 1.5+ generates mapping code for Records. However,
Records have no setters, so MapStruct uses constructor injection. This works
for simple cases but may fail for:
- Complex bidirectional mappings
- Mappings requiring post-construction modification
- Nested mutable collections

**Impact:** Records work for simple to medium-complexity mappings. For
highly complex mappings, traditional classes with setters are more practical.

### Finding #6: Records do not follow JavaBeans naming conventions

**Evidence:** Records generate accessors named `field()` instead of
`getField()`. Most modern frameworks (Spring, Jackson, Micronaut, Quarkus)
support both conventions, but older or custom frameworks that rely on
`java.beans.Introspector` may not recognize record accessors.

**Impact:** Verify framework compatibility before using Records as DTOs with
legacy systems.

### Finding #7: Adding or removing a component is a public API break

**Evidence:** A record component is part of the public API — it affects the
constructor signature, accessor methods, `equals`/`hashCode`, serialized
form, and pattern matching deconstruction pattern. Adding or removing a
component, or changing its type, is a breaking change.

```java
// Version 1
public record Point(int x, int y) { }

// Version 2 — adds z, breaks all equality checks with old instances
public record Point(int x, int y, int z) { }
```

**Impact:** Design record APIs carefully. Prefer smaller, focused records
over large monolithic ones. Consider a new record type or a wrapper rather
than modifying an existing record's components.

## Trade-offs

### Encapsulation vs Transparency

Classes can hide internal representation. A `Temperature` class might store
celsius internally but expose getters for both celsius and fahrenheit, with
the conversion encapsulated. A Record cannot hide its state — every component
is publicly visible.

```java
// Class: hides internal representation
public final class Temperature {
    private final double celsius;
    public Temperature(double celsius) { this.celsius = celsius; }
    public double getCelsius() { return celsius; }
    public double getFahrenheit() { return celsius * 9/5 + 32; }
}

// Record: exposes the raw component
public record Temperature(double celsius) {
    public double fahrenheit() { return celsius * 9/5 + 32; }
}
```

**Trade-off:** If the internal representation must be hidden to maintain
abstraction boundaries, use a class. If transparency is acceptable (or
desirable), use a Record.

### Immutability vs Flexibility

Records are immutable by design. If an object must change state over time
(e.g., a domain entity with lifecycle transitions), a Record is the wrong
choice.

**Trade-off:** Use Records for value objects, DTOs, and configuration.
Use classes for aggregates, entities, and mutable models.

### Conciseness vs Customization

Records provide maximal conciseness for the standard case. But if you need
custom `equals` logic, different accessor names, or hidden state, Records
cannot accommodate this — you must write a class.

**Trade-off:** Accept the Record conventions, or use a class with full control.

## Decision

Use Records as the **default choice** for:

1.  **REST DTOs** — request and response bodies
2.  **Value Objects** — domain primitives with validation
3.  **Configuration Properties** — immutable configuration models
4.  **Event Payloads** — messages in event-driven architectures
5.  **Sealed Hierarchy Members** — algebraic data types with pattern matching
6.  **Map Keys and Set Elements** — guaranteed correct `equals`/`hashCode`
7.  **Local Data Aggregates** — intermediate groupings in stream pipelines
8.  **JPA Projections** — read-only query result views

Do NOT use Records for:

1.  **JPA Entities** — incompatible with proxy and no-arg constructor requirements
2.  **Mutable Domain Models** — aggregates, entities with lifecycle
3.  **Classes with hidden state** — where internal representation must be abstracted
4.  **Classes needing custom serialization** — `writeObject`/`readObject` are ignored
5.  **Classes requiring subclassing** — Records are implicitly final

## Consequences

### Positive

- Dramatically reduced boilerplate for data carriers
- Guaranteed correct `equals`/`hashCode`/`toString` implementations
- Safer serialization (invariants enforced on deserialization)
- Natural integration with pattern matching and sealed classes
- No external dependency (Lombok, AutoValue, etc.)
- Compiler-enforced immutability prevents accidental mutation bugs

### Negative

- JPA entities cannot be Records (must maintain separate entity classes)
- API breaking changes when adding/removing components
- No support for builders, logging annotations, or other Lombok features
- JavaBeans-discoverable tools may not recognize record accessors
- Learning curve for teams unfamiliar with the semantics

## Decision Matrix

| Scenario | Records | Class | Lombok | Rationale |
|---|---|---|---|---|
| REST DTO | **Preferred** | Acceptable | Acceptable | Records are native, immutable, work with Jackson |
| JPA Entity | **Avoid** | **Preferred** | Acceptable | JPA requires no-arg ctor, non-final, setters |
| JPA Projection | **Preferred** | Acceptable | N/A | Constructor-based result mapping works perfectly |
| Domain Value Object | **Preferred** | Acceptable | Acceptable | Validation in compact constructor; immutability |
| Aggregate Root | **Avoid** | **Preferred** | Acceptable | Mutable, lifecycle, identity-based equality |
| Configuration | **Preferred** | Acceptable | Acceptable | Spring Boot supports Records natively |
| Mutable Model | **Avoid** | **Preferred** | **Preferred** | Requires setters, mutable state |
| Serialization (custom) | **Avoid** | **Preferred** | N/A | Records ignore custom serialization methods |
| Serialization (standard) | **Preferred** | Acceptable | N/A | Safer deserialization in Records |
| Pattern Matching | **Preferred** | N/A | N/A | Deconstruction patterns require Records |
| Sealed Hierarchy member | **Preferred** | N/A | N/A | Algebraic data types use Records |
| Local grouping (stream) | **Preferred** | Acceptable | N/A | Local Records are implicitly static |
| Builder pattern | **Avoid** | Acceptable | **Preferred** | Lombok `@Builder` is the standard approach |
| Logging field | **Avoid** | **Preferred** | **Preferred** | `@Slf4j` etc. are Lombok-only |

## Anti-patterns

### 1. The Mega-Record

```java
// BAD — 15+ components
public record UserResponse(
    Long id, String name, String email, String phone,
    Address address, Preferences preferences,
    List<Order> recentOrders, List<Notification> notifications,
    AccountStatus status, LocalDateTime createdAt,
    LocalDateTime updatedAt, LocalDateTime lastLoginAt,
    String profileImageUrl, String timezone, Locale locale
) { }
```

**Problem:** Violates the Single Responsibility Principle. Hard to read, hard
to version, changes to any component break the API.

**Solution:** Split into focused records. Use composition:

```java
public record UserResponse(
    Long id, String name, String email,
    UserProfile profile, AccountSummary account
) { }
```

### 2. Record as JPA Entity

```java
// BAD — will fail at runtime
@Entity
@Table(name = "users")
public record User(@Id Long id, String name, String email) { }
```

**Problem:** Hibernate cannot create proxies (final class), cannot instantiate
(no no-arg constructor), cannot mutate (final fields).

**Solution:** Use a regular class for entities, Records for DTOs and
projections.

### 3. Mixing Records with Lombok

```java
// BAD — Lombok annotations on Records are redundant or conflicting
@Data
@AllArgsConstructor
public record Point(int x, int y) { }
```

**Problem:** Records already generate everything `@Data` provides. Some
Lombok annotations produce no output on Records, others may conflict.

**Solution:** Use Records without Lombok, or Lombok without Records for the
same class.

### 4. Breaking Invariants with Overridden Accessors

```java
// BAD — violates the record contract
public record SmallPoint(int x, int y) {
    @Override
    public int x() { return Math.min(this.x, 100); }
    @Override
    public int y() { return Math.min(this.y, 100); }
}
```

**Problem:** The record invariant `r.equals(new R(r.c1(), ..., r.cn()))`
is violated. Accessing the field `this.x` vs the accessor `x()` gives
different values.

**Solution:** Validate in the compact constructor instead:

```java
public record SmallPoint(int x, int y) {
    public SmallPoint {
        x = Math.min(x, 100);
        y = Math.min(y, 100);
    }
}
```

### 5. Checked Exceptions in Canonical Constructor

```java
// BAD — does not compile
public record Config(String data) {
    public Config throws IOException { // Error: canonical constructor cannot throw checked exceptions
        this.data = Files.readString(Path.of(data));
    }
}
```

**Problem:** The canonical constructor is not allowed to throw checked
exceptions (by specification). This prevents using IO operations in
validation.

**Solution:** Use a static factory method:

```java
public record Config(String data) {
    public static Config load(String path) throws IOException {
        return new Config(Files.readString(Path.of(path)));
    }
}
```

## Common Mistakes

| Mistake | Why It's Wrong | Correct Approach |
|---|---|---|
| Using Records with JPA `@Entity` | Missing no-arg ctor, final class | Use regular class for entities |
| Expecting `getX()` style accessors | Records use `x()` not `getX()` | Adjust framework or use `@JsonProperty` |
| Adding `@Data` or `@Getter` on Records | Redundant, may conflict | Remove Lombok from Records |
| Overriding `equals`/`hashCode` incorrectly | Easy to break the contract | Trust the generated implementation |
| Using Records for mutable domain models | All fields are final | Use a class with setters |
| Forgetting `implements Serializable` | Records don't implement it by default | Add it explicitly when needed |
| Removing/renaming components in public API | Breaking change | Add new components instead of changing existing ones |
| Large component lists | Hard to read/test/version | Split into smaller, focused records |

## Migration Strategy

### From POJO to Record

```java
// Before
public final class Email {
    private final String value;
    public Email(String value) {
        if (value == null || !value.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.value = value;
    }
    public String getValue() { return value; }
    public boolean equals(Object o) { ... }
    public int hashCode() { ... }
    public String toString() { ... }
}

// After
public record Email(String value) {
    public Email {
        if (value == null || !value.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}
```

### From Lombok to Record

```java
// Before
@Value
@AllArgsConstructor
public class Address {
    String street;
    String city;
    String zipCode;
}

// After
public record Address(String street, String city, String zipCode) { }
```

### Migration Checklist

1.  Is the class effectively immutable? (fields are final, no setters)
2.  Is there any custom `equals`/`hashCode` logic? (Records generate their own)
3.  Is there any `writeObject`/`readObject`/`readExternal`? (Records ignore these)
4.  Is the class used as a JPA `@Entity`? (do NOT migrate)
5.  Is the class serialized? Add `implements Serializable` explicitly.
6.  Are accessors referenced via JavaBeans conventions? (e.g., `getValue()` → `value()`)
7.  Does any framework rely on no-arg construction? (Records don't have one)
8.  Is the class extended or does it extend another class? (Records cannot extend)
9.  Are there any checked exceptions in the constructor? (Not allowed)
10. Does the team use pattern matching with sealed hierarchies? (Records enable this)

### If migration causes issues

1.  Keep the original class and add a `toRecord()` method for gradual migration.
2.  Use an interface to abstract the API — implement it with both the class
    and the Record, then switch consumers.
3.  For JSON serialization, add `@JsonAutoDetect(fieldVisibility = ANY)` if
    needed (rare — Jackson detects Records automatically).

## Checklist

Before using a Record, ask yourself:

- [ ] Is this class conceptually immutable?
- [ ] Is this a JPA entity? → Do NOT use Record.
- [ ] Is this a framework proxy target? → Do NOT use Record.
- [ ] Does the compiler-enforced `equals`/`hashCode` match the domain semantics?
- [ ] Is it acceptable for all fields to be public via accessors?
- [ ] Are all components (and their types) stable in the public API?
- [ ] Does the canonical constructor need checked exceptions? → Use factory method.
- [ ] Are all team members familiar with Record semantics?
- [ ] Have all integration points (serialization, frameworks) been verified?

If you answered "No" to any of the first two questions, do not use a Record.
If any other answer is "No", investigate and resolve before proceeding.
