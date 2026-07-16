package com.jfranbob.modules.records.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.jfranbob.modules.records.valueobjects.Percentage;
import org.junit.jupiter.api.Test;

class PercentageTest {

    @Test
    void shouldCreateValidPercentage() {
        Percentage p = Percentage.of(50);
        assertEquals(50, p.value());
    }

    @Test
    void shouldConvertToFraction() {
        Percentage p = Percentage.of(25);
        assertEquals(0.25, p.asFraction(), 0.0001);
    }

    @Test
    void shouldAddPercentages() {
        Percentage a = Percentage.of(10);
        Percentage b = Percentage.of(20);
        assertEquals(30, a.add(b).value());
    }

    @Test
    void shouldRejectNegative() {
        assertThrows(IllegalArgumentException.class, () -> Percentage.of(-1));
    }

    @Test
    void shouldRejectOver100() {
        assertThrows(IllegalArgumentException.class, () -> Percentage.of(101));
    }

    @Test
    void shouldRejectAdditionOver100() {
        assertThrows(IllegalArgumentException.class, () -> Percentage.of(60).add(Percentage.of(50)));
    }

    @Test
    void shouldAllowBoundaries() {
        assertDoesNotThrow(() -> Percentage.of(0));
        assertDoesNotThrow(() -> Percentage.of(100));
    }
}
