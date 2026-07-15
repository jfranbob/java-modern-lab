package com.jfranbob.modules.records.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.jfranbob.modules.records.valueobjects.Email;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @Test
    void shouldCreateValidEmail() {
        Email email = new Email("user@example.com");
        assertEquals("user@example.com", email.value());
    }

    @Test
    void shouldExtractDomain() {
        Email email = new Email("user@example.com");
        assertEquals("example.com", email.domain());
    }

    @Test
    void shouldExtractLocalPart() {
        Email email = new Email("user@example.com");
        assertEquals("user", email.localPart());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "not-an-email", "@example.com", "user@", "user@.com"})
    void shouldRejectInvalidEmails(String invalidEmail) {
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    void shouldRejectNullEmail() {
        assertThrows(IllegalArgumentException.class, () -> new Email(null));
    }

    @Test
    void shouldBeEqualForSameValue() {
        Email email1 = new Email("user@example.com");
        Email email2 = new Email("user@example.com");
        assertEquals(email1, email2);
        assertEquals(email1.hashCode(), email2.hashCode());
    }

    @Test
    void shouldNotBeEqualForDifferentValue() {
        Email email1 = new Email("user@example.com");
        Email email2 = new Email("other@example.com");
        assertNotEquals(email1, email2);
    }
}
