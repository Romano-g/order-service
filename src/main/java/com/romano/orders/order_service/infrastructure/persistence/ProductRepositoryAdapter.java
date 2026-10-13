package com.romano.orders.order_service.infrastructure.persistence;

import com.romano.orders.order_service.application.port.ProductRepository;
import com.romano.orders.order_service.domain.model.Product;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepositoryAdapter implements ProductRepository {
    private final ProductJpaRepository jpaRepository;

    public ProductRepositoryAdapter(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        return jpaRepository
                .findById(productId)
                .map(ProductEntity::toDomain);
    }

    @Override
    public Product save(Product product) {
        return jpaRepository
                .save(ProductEntity.fromDomain(product))
                .toDomain();
    }
}
