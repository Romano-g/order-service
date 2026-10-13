package com.romano.orders.order_service.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    private final UUID id;
    private final String name;
    private BigDecimal price;
    private boolean active;

    public Product(String name, BigDecimal price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }

        this.id = UUID.randomUUID();
        this.name = name;
        changePrice(price);
        this.active = true;
    }

    private Product(UUID id, String name, BigDecimal price, boolean active) {
        if (id == null) {
            throw new IllegalArgumentException("O ID do produto é obrigatório.");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }

        this.id = id;
        this.name = name;
        changePrice(price);
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
    }

    public void changePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.signum() < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }

        this.price = newPrice;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public static Product reconstitute(UUID id, String name, BigDecimal price, boolean active) {
        return new Product(id, name, price, active);
    }
}
