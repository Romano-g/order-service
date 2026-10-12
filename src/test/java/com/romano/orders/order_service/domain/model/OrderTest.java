package com.romano.orders.order_service.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private Order order;
    private OrderItem item;

    @BeforeEach
    void setUp() {
        order = new Order(UUID.randomUUID());
        item = new OrderItem(UUID.randomUUID(), 10, new BigDecimal("10.00"));
    }

    @Test
    void shouldValidateEmptyOrder() {
        assertNotNull(order.getOrderId());
        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    void shouldAddItem() {
        order.addItem(item);

        assertIterableEquals(List.of(item), order.getItems());
    }

    @Test
    void shouldNotAddNullItem() {
        assertThrows(
                IllegalArgumentException.class,
                () -> order.addItem(null)
        );
    }

    @Test
    void shouldNotAddItemInOrderNotCreated() {
        order.cancel();
        assertThrows(
                IllegalStateException.class,
                () -> order.addItem(item)
        );
    }

    @Test
    void shouldRemoveItem() {
        OrderItem item2 = new OrderItem(
                UUID.randomUUID(), 10, new BigDecimal("5.00")
        );

        order.addItem(item);
        order.addItem(item2);

        order.removeItem(item);

        assertIterableEquals(List.of(item2), order.getItems());
    }

    @Test
    void shouldNotRemoveItemNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> order.removeItem(null)
        );
    }

    @Test
    void shouldNotRemoveItemInOrderNotCreated() {
        order.addItem(item);

        order.cancel();
        assertThrows(
                IllegalStateException.class,
                () -> order.removeItem(item)
        );
    }

    @Test
    void shouldNotRemoveItemNotInOrder() {
        assertThrows(
                IllegalArgumentException.class,
                () -> order.removeItem(item)
        );
    }

    @Test
    void shouldConfirmOrder() {
        order.addItem(item);
        order.confirm();

        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
    }

    @Test
    void shouldNotConfirmWhenStatusNotCreated() {
        order.addItem(item);
        order.cancel();

        assertThrows(
                IllegalStateException.class,
                () -> order.confirm()
        );
    }

    @Test
    void shouldNotConfirmWhenItemsIsEmpty() {
        assertThrows(
                IllegalStateException.class,
                () -> order.confirm()
        );
    }

    @Test
    void shouldCancelOrder() {
        order.cancel();

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void shouldNotCancelWhenOrderAlreadyCancelled() {
        order.cancel();
        assertThrows(
                IllegalStateException.class,
                () -> order.cancel()
        );
    }

    @Test
    void shouldReturnTotal() {
        OrderItem item2 = new OrderItem(
                UUID.randomUUID(), 10, new BigDecimal("5.00")
        );

        order.addItem(item);
        order.addItem(item2);

        assertEquals(0, new BigDecimal("150.00").compareTo(order.getTotal()));
    }

    @Test
    void shouldNotAllowExternalModificationOfItems() {
        order.addItem(item);

        assertThrows(
                UnsupportedOperationException.class,
                () -> order.getItems().clear()
        );

        assertEquals(1, order.getItems().size());
    }
}
