package com.jfranbob.modules.records.integration;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfranbob.modules.records.dto.UserResponse;
import com.jfranbob.modules.records.valueobjects.Email;
import java.util.List;
import org.junit.jupiter.api.Test;

class JacksonSerializationTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldSerializeRecordToJson() throws Exception {
        var user = new UserResponse(1L, "Alice", "alice@example.com", List.of("admin"), true);
        String json = mapper.writeValueAsString(user);
        assertTrue(json.contains("\"id\":1"));
        assertTrue(json.contains("\"name\":\"Alice\""));
        assertTrue(json.contains("\"active\":true"));
    }

    @Test
    void shouldDeserializeJsonToRecord() throws Exception {
        String json = """
            {
                "id": 1,
                "name": "Alice",
                "email": "alice@example.com",
                "roles": ["admin"],
                "active": true
            }
            """;
        UserResponse user = mapper.readValue(json, UserResponse.class);
        assertEquals(1L, user.id());
        assertEquals("Alice", user.name());
        assertTrue(user.active());
    }

    @Test
    void shouldRoundtripRecord() throws Exception {
        var original = new UserResponse(42L, "Bob", "bob@test.com", List.of("user"), false);
        String json = mapper.writeValueAsString(original);
        var restored = mapper.readValue(json, UserResponse.class);
        assertEquals(original, restored);
    }

    @Test
    void shouldAcceptMissingOptionalFields() throws Exception {
        String json = """
            {
                "id": 1,
                "name": "Alice",
                "email": null,
                "roles": null,
                "active": true
            }
            """;
        var user = mapper.readValue(json, UserResponse.class);
        assertEquals(1L, user.id());
        assertEquals("Alice", user.name());
    }

    @Test
    void shouldSerializeValueObjectRecord() throws Exception {
        var email = new Email("user@example.com");
        String json = mapper.writeValueAsString(email);
        assertTrue(json.contains("user@example.com"));
    }

    @Test
    void shouldDeserializeValueObjectRecord() throws Exception {
        String json = """
            {"value": "test@example.com"}
            """;
        Email email = mapper.readValue(json, Email.class);
        assertEquals("test@example.com", email.value());
    }

    @Test
    void shouldRoundtripValueObject() throws Exception {
        var original = new Email("roundtrip@test.com");
        String json = mapper.writeValueAsString(original);
        var restored = mapper.readValue(json, Email.class);
        assertEquals(original, restored);
    }

    @Test
    void shouldSerializeEmptyRoles() throws Exception {
        var user = new UserResponse(1L, "NoRoles", "noroles@test.com", List.of(), true);
        String json = mapper.writeValueAsString(user);
        assertTrue(json.contains("\"roles\":[]"));
    }
}
