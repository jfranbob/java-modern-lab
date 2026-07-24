package com.jfranbob.modules.records.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.jfranbob.modules.records.dto.ErrorResponse;
import com.jfranbob.modules.records.dto.UserResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

class DtoTest {

    @Test
    void shouldCreateUserResponse() {
        var user = new UserResponse(1L, "Alice", "alice@example.com", List.of("admin"), true);
        assertEquals(1L, user.id());
        assertEquals("Alice", user.name());
        assertTrue(user.active());
    }

    @Test
    void shouldCreateErrorResponseViaFactory() {
        var error = ErrorResponse.of(404, "Not Found", "Resource not found", "/api/users");
        assertEquals(404, error.status());
        assertEquals("Not Found", error.error());
        assertNotNull(error.timestamp());
    }

    @Test
    void userResponseEqualsAndHashCode() {
        var a = new UserResponse(1L, "A", "a@x.com", List.of(), true);
        var b = new UserResponse(1L, "A", "a@x.com", List.of(), true);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void userResponseToString() {
        var user = new UserResponse(1L, "Alice", "a@x.com", List.of(), true);
        String str = user.toString();
        assertTrue(str.contains("Alice"));
        assertTrue(str.contains("a@x.com"));
    }
}
