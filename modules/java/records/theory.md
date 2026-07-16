# Java Records — Theory

## Motivation

Why do Records exist? The standard argument is "reducing boilerplate", but the
designers of JEP 395 had a deeper goal.

Before Records, every Java developer had to:

1.  Write a class with private final fields.
2.  Write a constructor that assigns each parameter to each field.
3.  Write getters for each field.
4.  Override `equals()` and `hashCode()` (correctly — which is error-prone).
5.  Override `toString()`.
6.  Make sure the class is `final` to prevent extension that could break
    invariants.
7.  Fight the temptation to skip steps 3–5 for speed.

IDEs generate this code, but generated code is still code that must be read,
reviewed, and maintained. Worse, the reader cannot easily discern the intent:
"is this class a data carrier or does it have behavior?"

Records solve this by making the _intent_ part of the declaration:

```java
record Point(int x, int y) {}
```

The header says: "This is a transparent carrier for an x and a y value.
Everything else follows from that."

## Historical Context

### POJO (Java 1.0–14)

The manual approach. Correct, but verbose and error-prone. Every team wrote
their own conventions and many skipped `equals`/`hashCode`.

```java
public final class Point {
    private final int x;
    private final int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int x() { return x; }
    public int y() { return y; }
    @Override public boolean equals(Object o) { ... }
    @Override public int hashCode() { return Objects.hash(x, y); }
    @Override public String toString() { return "Point[x=" + x + ", y=" + y + "]"; }
}
```

### Lombok (~2010–present)

Annotation-based code generation. Reduces verbosity but requires a build-time
dependency and IDE plugin. Works with any Java version, supports mutability.

```java
@Value
public class Point {
    int x;
    int y;
}
```

### AutoValue / Immutables (~2014–present)

Annotation-based value types from Google/immutables.github.io. Generate
immutable implementations via annotation processing at compile time. Used
heavily in Android and Google-internal projects.

### Kotlin Data Classes (2016–present)

JVM language with first-class data class support. Inspired parts of the Records
design.

```kotlin
data class Point(val x: Int, val y: Int)
```

### Java Records (JDK 16, 2021)

A language-native solution that requires no dependencies and provides stronger
semantic guarantees than any of the above.

## Language Semantics

### Record Declaration

A record class is declared with a header that lists its _components_:

```java
public record Point(int x, int y) { }
```

This single line is equivalent to approximately 60 lines of hand-written code.

### Implicit Members

The compiler automatically generates:

- **Private final fields**: one per component (`private final int x;`)
- **Canonical constructor**: assigns each parameter to the corresponding field
- **Accessor methods**: same name and type as the component (`x()`, `y()`)
- **`equals()`**: two records are equal if they are the same type and all
  components are equal
- **`hashCode()`**: consistent with `equals`, using all components
- **`toString()`**: includes the type name and all component names/values

### Restrictions

| Restriction | Rationale |
|---|---|
| `extends` clause not allowed | Superclass is always `java.lang.Record` |
| Implicitly `final` | Prevents fragile subclassing |
| Cannot be `abstract` | API is fully defined by the header |
| Instance fields cannot be declared | State is only the header components |
| No instance initializers | Prevent hidden state |
| No `native` methods | Prevent external state dependencies |
| Nested records are implicitly `static` | Prevent hidden enclosing instance state |

### Canonical Constructor

The canonical constructor has the same signature as the record header. It can
be declared explicitly in two forms:

**Standard form:**
```java
public record Range(int lo, int hi) {
    public Range(int lo, int hi) {
        if (lo > hi) throw new IllegalArgumentException(...);
        this.lo = lo;
        this.hi = hi;
    }
}
```

**Compact form** (assignment happens automatically at the end):
```java
public record Range(int lo, int hi) {
    public Range {
        if (lo > hi) throw new IllegalArgumentException(...);
    }
}
```

The compact form is preferred because it makes the intent clear: "validate and
normalize, but the assignment is automatic and guaranteed."

### Instance Methods

Records can declare instance methods:

```java
public record Money(BigDecimal amount, Currency currency) {
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }
}
```

This is _not_ an anti-pattern. Records are not "dumb DTOs". They can contain
behavior as long as it preserves their invariants. However, if the behavior
requires mutable or hidden state, a regular class is a better choice.

### Static Members

Records can declare static fields, methods, and nested types:

```java
public record Celsius(double value) {
    private static final double ABSOLUTE_ZERO = -273.15;

    public Celsius {
        if (value < ABSOLUTE_ZERO) {
            throw new IllegalArgumentException("Below absolute zero");
        }
    }

    public static Celsius fromFahrenheit(double f) {
        return new Celsius((f - 32) * 5 / 9);
    }
}
```

### Generic Records

Records support type parameters:

```java
public record Pair<T, U>(T first, U second) { }
```

## Bytecode Representation

When the compiler processes:

```java
record Point(int x, int y) { }
```

It generates bytecode equivalent to:

```java
public final class Point extends java.lang.Record {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() { return this.x; }
    public int y() { return this.y; }

    public boolean equals(Object o) { ... }
    public int hashCode() { ... }
    public String toString() { ... }
}
```

The class file also contains a `Record` attribute (new in JDK 16) that stores
component metadata for reflection and serialization.

There is no performance overhead at the JVM level compared to a hand-written
class. The generated methods are concrete, non-synthetic, and inline-friendly.

## Reflection

Two methods added to `java.lang.Class`:

```java
Point.class.isRecord();             // true
Point.class.getRecordComponents();  // [x, y]
```

`java.lang.reflect.RecordComponent` provides access to the component name,
type, generic type, annotations, and accessor method.

## Serialization

See `example/serialization/` for code. Key points:

- Records can implement `Serializable` like any class.
- The serialized form is always the component values — no customization.
- **Deserialization invokes the canonical constructor**, not the no-arg
  superclass constructor + reflection. This means invariants are enforced.
- `writeObject`, `readObject`, `readObjectNoData`, `writeExternal`,
  `readExternal` are all silently ignored.
- `serialVersionUID` is not required (the requirement is waived for records;
  defaults to `0L`).

This is a security improvement over traditional serialization, where an
attacker can craft a byte stream that bypasses constructor validation,
creating "impossible objects".

## Pattern Matching

Records were designed to work with pattern matching (added in JDK 21 as
standard). Deconstruction patterns allow extracting components directly:

```java
if (obj instanceof Point(int x, int y)) {
    System.out.println(x + ", " + y);
}

String describe(Object obj) {
    return switch (obj) {
        case Point(int x, int y) -> "Point at " + x + "," + y;
        case Circle(double r)    -> "Circle of radius " + r;
        default                  -> "Unknown";
    };
}
```

This is the foundation for the algebraic data type style using sealed
interfaces + records (see `example/sealedhierarchies/`).

## Performance

Performance is analyzed in depth in `decision.md` (#Findings). In summary:

- Allocation, access, equality: equivalent to hand-coded classes.
- No reflection overhead (methods are concrete).
- Jackson serialization is actually faster because records have canonical
  construction paths.
- The only performance concern is high-allocation-rate scenarios (>100k
  objects/sec on constrained heap), where GC pressure can increase latency.
  This is a GC tuning concern, not a record concern.

## Compatibility

### Spring / Jackson

Records work transparently with Spring Boot 3+ and Jackson 2.14+.
Jackson uses the canonical constructor for deserialization, which means
validation in the compact constructor is always enforced.

### JPA / Hibernate

Records cannot be JPA entities because:

- JPA requires a no-argument constructor (public or protected).
- JPA entities must not be `final` (Hibernate uses proxies).
- JPA entities need setters or field-level mutation.

Records _can_ be used as read-only JPA projections with constructor-based
result mapping:

```java
// In repository:
List<NameOnly> findAllByName(String name);

// As record:
public record NameOnly(String name) { }
```

### MapStruct

MapStruct 1.5+ supports records as targets. However, complex bidirectional
mappings may not work because records lack setters.

### JavaBeans

Records do not follow the JavaBeans naming convention (`getName()` instead of
`name()`). Some older frameworks that depend on JavaBeans introspection will
not recognize record accessors. Modern frameworks (Spring, Jackson, Micronaut,
Quarkus) support both conventions.

## Common Misconceptions

| Misconception | Truth |
|---|---|
| "Records are just syntax sugar" | They enforce semantic constraints the compiler checks |
| "Records cannot have methods" | They can have instance and static methods |
| "Records are always the best choice" | They have clear limitations (JPA, mutability, inheritance) |
| "Records replace Lombok completely" | Lombok still handles mutability, builders, logging |
| "Records are slow because of reflection" | Accessor methods are concrete, not reflective |
| "Records cannot be serialized" | They implement Serializable and deserialize more safely |
