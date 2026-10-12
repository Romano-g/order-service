package com.romano.orders.order_service.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.UUID;

public class OrderItemTest {
    @Test
    void shouldCreateOrderItem() {
        OrderItem order = new OrderItem(
                UUID.randomUUID(), 10, new BigDecimal("10.00")
        );

        assertNotNull(order.getProductId());
        assertEquals(10, order.getQuantity());
        assertEquals(new BigDecimal("10.00"), order.getUnitPrice());
        assertEquals(0, new BigDecimal("100.00").compareTo(order.getTotal()));
    }

    @Test
    void shouldNotCreateIdNull() {
        assertThrows(
                IllegalArgumentException.class, () -> new OrderItem(null, 10, new BigDecimal("10.00"))
        );
    }

    @Test
    void shouldNotCreateQuantityEqualZero() {
        assertThrows(
                IllegalArgumentException.class, () -> new OrderItem(UUID.randomUUID(), 0, new BigDecimal("10.00"))
        );
    }

    @Test
    void shouldNotCreateQuantityMinorThanZero() {
        assertThrows(
                IllegalArgumentException.class, () -> new OrderItem(UUID.randomUUID(), -1, new BigDecimal("10.00"))
        );
    }

    @Test
    void shouldNotCreateValueNull() {
        assertThrows(
                IllegalArgumentException.class, () -> new OrderItem(UUID.randomUUID(), 10, null)
        );
    }

    @Test
    void shouldNotCreateValueEqualZero() {
        assertThrows(
                IllegalArgumentException.class, () -> new OrderItem(UUID.randomUUID(), 10, new BigDecimal("0.00"))
        );
    }

    @Test
    void shouldNotCreateValueMinorThanZero() {
        assertThrows(
                IllegalArgumentException.class, () -> new OrderItem(UUID.randomUUID(), 10, new BigDecimal("-1.00"))
        );
    }
}
