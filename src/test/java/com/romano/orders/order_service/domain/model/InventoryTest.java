package com.romano.orders.order_service.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    @Test
    void shouldCreateStockWithZeroQuantity() {
        Inventory inventory = new Inventory(
                UUID.randomUUID(), 0
        );

        assertNotNull(inventory.getProductId());
        assertEquals(0, inventory.getQuantity());
    }

    @Test
    void shouldCreateStockWithQuantity() {
        Inventory inventory = new Inventory(
                UUID.randomUUID(), 10
        );

        assertNotNull(inventory.getProductId());
        assertEquals(10, inventory.getQuantity());
    }

    @Test
    void shouldRejectNullUUID() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Inventory(null, 0)
        );
    }

    @Test
    void shouldAddStock() {
        Inventory inventory = new Inventory(
                UUID.randomUUID(), 0
        );

        inventory.addStock(10);

        assertEquals(10, inventory.getQuantity());
    }

    @Test
    void shouldRejectZeroAddStock() {
        Inventory inventory = new Inventory(
                UUID.randomUUID(), 0
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> inventory.addStock(0)
        );
    }

    @Test
    void shouldRemoveStock() {
        Inventory inventory = new Inventory(
                UUID.randomUUID(), 20
        );

        inventory.removeStock(20);
        assertEquals(0, inventory.getQuantity());
    }

    @Test
    void shouldNotRemoveQuantityGreaterThanStock() {
        Inventory inventory = new Inventory(
                UUID.randomUUID(), 20
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> inventory.removeStock(21)
        );
    }

    @Test
    void shouldNotRemoveZeroFromStock() {
        Inventory inventory = new Inventory(
                UUID.randomUUID(), 20
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> inventory.removeStock(0)
        );
    }

    @Test
    void shouldRejectNegativeInitialQuantity() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Inventory(UUID.randomUUID(), -1)
        );
    }

    @Test
    void shouldRejectNegativeStockAddition() {
        Inventory inventory = new Inventory(UUID.randomUUID(), 10);

        assertThrows(
                IllegalArgumentException.class,
                () -> inventory.addStock(-1)
        );

        assertEquals(10, inventory.getQuantity());
    }

    @Test
    void shouldRejectNegativeStockRemoval() {
        Inventory inventory = new Inventory(UUID.randomUUID(), 10);

        assertThrows(
                IllegalArgumentException.class,
                () -> inventory.removeStock(-1)
        );

        assertEquals(10, inventory.getQuantity());
    }

    @Test
    void shouldPreserveStockWhenRemovingMoreThanAvailable() {
        Inventory inventory = new Inventory(UUID.randomUUID(), 10);

        assertThrows(
                IllegalArgumentException.class,
                () -> inventory.removeStock(11)
        );

        assertEquals(10, inventory.getQuantity());
    }

    @Test
    void shouldRemovePartialStock() {
        Inventory inventory = new Inventory(UUID.randomUUID(), 10);

        inventory.removeStock(5);
        assertEquals(5, inventory.getQuantity());
    }
}
