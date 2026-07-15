package com.jfranbob.modules.records.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.jfranbob.modules.records.valueobjects.Money;
import java.math.BigDecimal;
import java.util.Currency;
import org.junit.jupiter.api.Test;

class MoneyTest {

    @Test
    void shouldCreateMoney() {
        Money money = Money.of(100.50, "USD");
        assertEquals(0, BigDecimal.valueOf(100.50).compareTo(money.amount()));
        assertEquals(Currency.getInstance("USD"), money.currency());
    }

    @Test
    void shouldAddSameCurrency() {
        Money a = Money.of(10.00, "USD");
        Money b = Money.of(20.00, "USD");
        Money result = a.add(b);
        assertEquals(0, BigDecimal.valueOf(30.00).compareTo(result.amount()));
    }

    @Test
    void shouldRejectDifferentCurrencyAddition() {
        Money a = Money.of(10.00, "USD");
        Money b = Money.of(20.00, "EUR");
        assertThrows(IllegalArgumentException.class, () -> a.add(b));
    }

    @Test
    void shouldSubtractSameCurrency() {
        Money a = Money.of(30.00, "USD");
        Money b = Money.of(10.00, "USD");
        Money result = a.subtract(b);
        assertEquals(0, BigDecimal.valueOf(20.00).compareTo(result.amount()));
    }

    @Test
    void shouldMultiply() {
        Money money = Money.of(10.00, "USD");
        Money result = money.multiply(BigDecimal.valueOf(3));
        assertEquals(0, BigDecimal.valueOf(30.00).compareTo(result.amount()));
    }

    @Test
    void shouldCompareValues() {
        Money a = Money.of(100.00, "USD");
        Money b = Money.of(50.00, "USD");
        assertTrue(a.isGreaterThan(b));
        assertFalse(b.isGreaterThan(a));
    }

    @Test
    void shouldRejectNullAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Money(null, Currency.getInstance("USD")));
    }

    @Test
    void shouldRejectNullCurrency() {
        assertThrows(IllegalArgumentException.class, () -> new Money(BigDecimal.TEN, null));
    }
}
