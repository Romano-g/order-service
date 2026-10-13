package com.romano.orders.order_service.infrastructure.persistence;

import com.romano.orders.order_service.application.port.ProductRepository;
import com.romano.orders.order_service.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductRepositoryAdapterTest {
    @Autowired
    private ProductRepository repository;

    @Test
    void shouldSaveValidProduct() {
        var product = new Product(
                "Teclado", new BigDecimal("100.00")
        );

        repository.save(product);
        var savedProduct = repository.findById(product.getId());
        assertTrue(savedProduct.isPresent());

        var actual = savedProduct.orElseThrow();
        assertEquals(product.getId(), actual.getId());
        assertEquals(product.getName(), actual.getName());
        assertEquals(product.getPrice(), actual.getPrice());
        assertEquals(product.isActive(), actual.isActive());
    }

    @Test
    void shouldUpdateProductPrice() {
        var product = new Product(
                "Kitkat", new BigDecimal("10.00")
        );

        repository.save(product);
        product.changePrice(new BigDecimal("15.00"));
        repository.save(product);
        var actual = repository.findById(product.getId()).orElseThrow();

        assertEquals(product.getId(), actual.getId());
        assertEquals(product.getName(), actual.getName());
        assertEquals(new BigDecimal("15.00"), actual.getPrice());
        assertEquals(product.isActive(), actual.isActive());
    }
}
