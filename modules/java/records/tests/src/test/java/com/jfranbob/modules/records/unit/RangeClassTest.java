package com.jfranbob.modules.records.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.jfranbob.modules.records.serialization.RangeClass;
import java.io.*;
import org.junit.jupiter.api.Test;

class RangeClassTest {

    @Test
    void shouldCreateValidRange() {
        var range = new RangeClass(1, 10);
        assertEquals(1, range.lo());
        assertEquals(10, range.hi());
    }

    @Test
    void shouldRejectInvalidRange() {
        assertThrows(IllegalArgumentException.class, () -> new RangeClass(10, 1));
    }

    @Test
    void shouldAllowEqualBounds() {
        var range = new RangeClass(5, 5);
        assertEquals(5, range.lo());
        assertEquals(5, range.hi());
    }

    @Test
    void shouldImplementEquals() {
        var a = new RangeClass(1, 10);
        var b = new RangeClass(1, 10);
        var c = new RangeClass(1, 20);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    @Test
    void equalsShouldHandleSameObject() {
        var a = new RangeClass(1, 10);
        assertEquals(a, a);
    }

    @Test
    void equalsShouldHandleNull() {
        var a = new RangeClass(1, 10);
        assertNotEquals(null, a);
    }

    @Test
    void equalsShouldHandleDifferentType() {
        var a = new RangeClass(1, 10);
        assertNotEquals("string", a);
    }

    @Test
    void shouldImplementToString() {
        var range = new RangeClass(1, 10);
        String str = range.toString();
        assertTrue(str.contains("1"));
        assertTrue(str.contains("10"));
    }

    @Test
    void shouldSerializeAndDeserialize() throws Exception {
        var original = new RangeClass(1, 10);
        var bos = new ByteArrayOutputStream();
        try (var oos = new ObjectOutputStream(bos)) {
            oos.writeObject(original);
        }
        var bis = new ByteArrayInputStream(bos.toByteArray());
        try (var ois = new ObjectInputStream(bis)) {
            var restored = (RangeClass) ois.readObject();
            assertEquals(original, restored);
            assertEquals(original.lo(), restored.lo());
            assertEquals(original.hi(), restored.hi());
        }
    }
}
