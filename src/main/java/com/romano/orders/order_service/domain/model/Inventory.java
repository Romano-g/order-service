package com.romano.orders.order_service.domain.model;

import java.util.UUID;

public class Inventory {
    private final UUID productId;
    private int quantity;

    public Inventory(UUID productId, int quantity) {
        if (productId == null) throw new IllegalArgumentException("O productId não pode ser null.");
        if (quantity < 0) throw new IllegalArgumentException("A quantidade não pode ser menor que 0.");

        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que 0.");
        }

        this.quantity += quantity;
    }

    public void removeStock(int quantity) {
        int actualQuantity = getQuantity();

        if (quantity > actualQuantity || quantity <= 0) {
            throw new IllegalArgumentException("A quantidade a ser retirada deve ser menor que a quantidade disponivel e maior que zero.");
        }

        this.quantity -= quantity;
    }
}
