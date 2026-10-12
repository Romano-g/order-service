package com.romano.orders.order_service.application.usecase;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class CreateOrderItemCommand {
    @NotNull
    private final UUID productId;
    @Positive
    private final int quantity;

    public CreateOrderItemCommand(UUID productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
