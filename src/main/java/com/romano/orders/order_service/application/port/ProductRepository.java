package com.romano.orders.order_service.application.port;

import com.romano.orders.order_service.domain.model.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Optional<Product> findById(UUID productId);
    Product save(Product product);
}
