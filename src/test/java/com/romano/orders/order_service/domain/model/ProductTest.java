package com.romano.orders.order_service.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class ProductTest {
    @Test
    void shouldCreateProductWithValidData() {
        Product product = new Product(
                "Teclado",
                new BigDecimal("199.90")
        );

        assertNotNull(product.getId());
        assertEquals("Teclado", product.getName());
        assertEquals(0, new BigDecimal("199.90").compareTo(product.getPrice()));
        assertTrue(product.isActive());
    }

    @Test
    void shouldRejectBlankProductName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(" ", new BigDecimal("199.90"))
        );
    }

    @Test
    void shouldRejectNegativePrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product("Teclado", new BigDecimal("-10.00"))
        );
    }

    @Test
    void shouldRejectNullPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product("Teclado", null)
        );
    }

    @Test
    void shouldRejectNullName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(null, new BigDecimal("199.90"))
        );
    }

    @Test
    void shouldChangePriceToValidValue() {
        Product product = new Product(
                "Teclado",
                new BigDecimal("199.90")
        );

        product.changePrice(new BigDecimal("249.90"));

        assertEquals(
                0,
                new BigDecimal("249.90").compareTo(product.getPrice())
        );
    }

    @Test
    void shouldRejectNegativePriceWhenChangingPrice() {
        Product product = new Product(
                "Teclado",
                new BigDecimal("199.90")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> product.changePrice(new BigDecimal("-10.00"))
        );
    }

    @Test
    void shouldDeactivateProduct() {
        Product product = new Product(
                "Teclado",
                new BigDecimal("199.90")
        );

        product.deactivate();

        assertFalse(product.isActive());
    }

    @Test
    void shouldActivateInactiveProduct() {
        Product product = new Product(
                "Teclado",
                new BigDecimal("199.90")
        );

        product.deactivate();
        product.activate();

        assertTrue(product.isActive());
    }

    @Test
    void shouldKeepOriginalPriceWhenNewPriceIsInvalid() {
        Product product = new Product(
                "Teclado",
                new BigDecimal("199.90")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> product.changePrice(new BigDecimal("-10.00"))
        );

        assertEquals(
                0,
                new BigDecimal("199.90").compareTo(product.getPrice())
        );
    }
}
