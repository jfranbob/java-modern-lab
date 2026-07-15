package com.jfranbob.modules.records.integration;

import static org.junit.jupiter.api.Assertions.*;

import com.jfranbob.modules.records.serialization.PersonRecord;
import com.jfranbob.modules.records.serialization.RangeRecord;
import java.io.*;
import org.junit.jupiter.api.Test;

class SerializationTest {

    @Test
    void shouldSerializeAndDeserializeRecord() throws Exception {
        var original = new PersonRecord("Alice", 30);
        var deserialized = serializeAndDeserialize(original);
        assertEquals(original, deserialized);
        assertEquals("Alice", deserialized.name());
        assertEquals(30, deserialized.age());
    }

    @Test
    void shouldSerializeAndDeserializeRangeRecord() throws Exception {
        var original = new RangeRecord(1, 10);
        var deserialized = serializeAndDeserialize(original);
        assertEquals(original, deserialized);
    }

    @Test
    void shouldSerializeRecordWithValidValues() throws Exception {
        var range = new RangeRecord(1, 100);
        var deserialized = serializeAndDeserialize(range);
        assertEquals(1, deserialized.lo());
        assertEquals(100, deserialized.hi());
    }

    @Test
    void recordEnforcesInvariantOnDeserialization() {
        assertThrows(IllegalArgumentException.class, () -> new RangeRecord(100, 1));
    }

    @Test
    void recordEqualsAfterSerialization() throws Exception {
        var r1 = new PersonRecord("Bob", 25);
        var r2 = serializeAndDeserialize(r1);
        assertEquals(r1, r2);
    }

    @Test
    void multipleRecordFieldsSerialize() throws Exception {
        var original = new PersonRecord("Charlie", 35);
        var restored = serializeAndDeserialize(original);
        assertAll(
                () -> assertEquals(original.name(), restored.name()),
                () -> assertEquals(original.age(), restored.age()),
                () -> assertEquals(original.hashCode(), restored.hashCode()));
    }

    @SuppressWarnings("unchecked")
    private <T extends Serializable> T serializeAndDeserialize(T object) throws Exception {
        var bos = new ByteArrayOutputStream();
        try (var oos = new ObjectOutputStream(bos)) {
            oos.writeObject(object);
        }
        var bis = new ByteArrayInputStream(bos.toByteArray());
        try (var ois = new ObjectInputStream(bis)) {
            return (T) ois.readObject();
        }
    }
}
