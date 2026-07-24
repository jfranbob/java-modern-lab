package com.jfranbob.modules.records.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.jfranbob.modules.records.dto.CreateOrderRequest;
import com.jfranbob.modules.records.dto.CreateOrderRequest.OrderLineItem;
import java.util.List;
import org.junit.jupiter.api.Test;

class CreateOrderRequestTest {

    @Test
    void shouldCreateValidOrder() {
        var item = new OrderLineItem(1L, 2, 10.99);
        var order = new CreateOrderRequest(42L, List.of(item), "USD");
        assertEquals(42L, order.customerId());
        assertEquals(1, order.items().size());
    }

    @Test
    void shouldRejectNullCustomer() {
        var item = new OrderLineItem(1L, 2, 10.99);
        assertThrows(IllegalArgumentException.class, () -> new CreateOrderRequest(null, List.of(item), "USD"));
    }

    @Test
    void shouldRejectEmptyItems() {
        assertThrows(IllegalArgumentException.class, () -> new CreateOrderRequest(1L, List.of(), "USD"));
    }

    @Test
    void shouldRejectBlankCurrency() {
        var item = new OrderLineItem(1L, 2, 10.99);
        assertThrows(IllegalArgumentException.class, () -> new CreateOrderRequest(1L, List.of(item), ""));
    }

    @Test
    void shouldValidateOrderLineItem() {
        assertThrows(IllegalArgumentException.class, () -> new OrderLineItem(null, 1, 10.0));
        assertThrows(IllegalArgumentException.class, () -> new OrderLineItem(1L, 0, 10.0));
        assertThrows(IllegalArgumentException.class, () -> new OrderLineItem(1L, -1, 10.0));
        assertThrows(IllegalArgumentException.class, () -> new OrderLineItem(1L, 1, 0.0));
    }
}
