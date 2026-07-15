package com.jfranbob.modules.records.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.jfranbob.modules.records.migration.AfterRecord;
import com.jfranbob.modules.records.migration.BeforeRecord;
import org.junit.jupiter.api.Test;

class MigrationTest {

    @Test
    void beforeRecordShouldWork() {
        var before = new BeforeRecord("hello");
        assertEquals("hello", before.getValue());
    }

    @Test
    void afterRecordShouldWork() {
        var after = new AfterRecord("hello");
        assertEquals("hello", after.value());
    }

    @Test
    void bothShouldStoreSameValue() {
        var before = new BeforeRecord("test");
        var after = new AfterRecord("test");
        assertEquals(before.getValue(), after.value());
    }

    @Test
    void afterRecordShouldPreserveValidation() {
        assertThrows(IllegalArgumentException.class, () -> new AfterRecord(""));
        assertThrows(IllegalArgumentException.class, () -> new AfterRecord(null));
    }

    @Test
    void afterRecordShouldSupportMethods() {
        var after = new AfterRecord("hello");
        assertEquals("HELLO", after.toUpperCase());
    }

    @Test
    void beforeRecordShouldRejectInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new BeforeRecord(""));
        assertThrows(IllegalArgumentException.class, () -> new BeforeRecord(null));
    }

    @Test
    void beforeRecordEqualsAndHashCode() {
        var a = new BeforeRecord("hello");
        var b = new BeforeRecord("hello");
        var c = new BeforeRecord("world");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    @Test
    void beforeRecordEqualsShouldHandleSameObject() {
        var a = new BeforeRecord("hello");
        assertEquals(a, a);
    }

    @Test
    void beforeRecordEqualsShouldHandleNull() {
        var a = new BeforeRecord("hello");
        assertNotEquals(null, a);
    }

    @Test
    void beforeRecordEqualsShouldHandleDifferentType() {
        var a = new BeforeRecord("hello");
        assertNotEquals("string", a);
    }

    @Test
    void beforeRecordToString() {
        var before = new BeforeRecord("hello");
        String str = before.toString();
        assertTrue(str.contains("hello"));
    }
}
