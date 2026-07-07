package com.jfranbob.modules.modulename;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void greetShouldReturnGreeting() {
        String result = Main.greet("World");
        assertEquals("Hello, World!", result);
    }
}
