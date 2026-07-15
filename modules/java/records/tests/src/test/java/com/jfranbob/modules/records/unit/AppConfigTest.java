package com.jfranbob.modules.records.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.jfranbob.modules.records.spring.AppConfig;
import org.junit.jupiter.api.Test;

class AppConfigTest {

    @Test
    void shouldCreateValidConfig() {
        var config = new AppConfig("myapp", "production", 100, true);
        assertEquals("myapp", config.name());
        assertEquals("production", config.environment());
        assertEquals(100, config.maxConnections());
        assertTrue(config.featuresEnabled());
    }

    @Test
    void shouldRejectBlankName() {
        assertThrows(IllegalArgumentException.class, () -> new AppConfig("", "prod", 10, true));
    }

    @Test
    void shouldRejectBlankEnvironment() {
        assertThrows(IllegalArgumentException.class, () -> new AppConfig("app", "", 10, true));
    }

    @Test
    void shouldRejectInvalidMaxConnections() {
        assertThrows(IllegalArgumentException.class, () -> new AppConfig("app", "prod", 0, true));
    }
}
