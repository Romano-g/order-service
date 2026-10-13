package com.romano.orders.order_service.infrastructure.persistence;

import com.romano.orders.order_service.domain.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    private UUID id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "active", nullable = false)
    private boolean active;

    private ProductEntity(UUID id, String name, BigDecimal price, boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
    }

    protected ProductEntity() {}

    public Product toDomain() {
        return Product.reconstitute(
                id, name, price, active
        );
    }

    public static ProductEntity fromDomain(Product product) {
        return new ProductEntity(
                product.getId(), product.getName(), product.getPrice(), product.isActive()
        );
    }

    public UUID getId() {
        return id;
    }
}
