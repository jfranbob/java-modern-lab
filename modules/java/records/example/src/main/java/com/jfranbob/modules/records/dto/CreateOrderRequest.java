package com.jfranbob.modules.records.dto;

import java.util.List;

public record CreateOrderRequest(Long customerId, List<OrderLineItem> items, String currency) {

    public CreateOrderRequest {
        if (customerId == null) {
            throw new IllegalArgumentException("customerId must not be null");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("items must not be null or empty");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("currency must not be blank");
        }
        items = List.copyOf(items);
    }

    public record OrderLineItem(Long productId, int quantity, double unitPrice) {

        public OrderLineItem {
            if (productId == null) {
                throw new IllegalArgumentException("productId must not be null");
            }
            if (quantity <= 0) {
                throw new IllegalArgumentException("quantity must be positive");
            }
            if (unitPrice <= 0) {
                throw new IllegalArgumentException("unitPrice must be positive");
            }
        }
    }
}
