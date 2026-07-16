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
    void recordEnforcesInvariantOnDeserialization() throws Exception {
        var valid = new RangeRecord(1, 10);
        var bos = new ByteArrayOutputStream();
        try (var oos = new ObjectOutputStream(bos)) {
            oos.writeObject(valid);
        }
        var data = bos.toByteArray();

        int offset = findFieldOffset(data, 10, 1);
        if (offset >= 0) {
            data[offset + 3] = 1; // hi = 1
            data[offset + 7] = 100; // lo = 100
        }

        assertThrows(InvalidObjectException.class, () -> {
            try (var ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
                ois.readObject();
            }
        });
    }

    private int findFieldOffset(byte[] data, int hi, int lo) {
        for (int i = 0; i < data.length - 7; i++) {
            if (data[i] == 0
                    && data[i + 1] == 0
                    && data[i + 2] == 0
                    && data[i + 3] == hi
                    && data[i + 4] == 0
                    && data[i + 5] == 0
                    && data[i + 6] == 0
                    && data[i + 7] == lo) {
                return i;
            }
        }
        return -1;
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
