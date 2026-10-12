package com.romano.orders.order_service.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {
    private final UUID productId;
    private final int quantity;
    private final BigDecimal unitPrice;

    public OrderItem(UUID productId, int quantity, BigDecimal unitPrice) {
        if (productId == null) throw new IllegalArgumentException("O productId não pode ser null.");
        if (quantity <= 0) throw new IllegalArgumentException("A quantidade deve ser maior que 0.");
        if (unitPrice == null || unitPrice.signum() <= 0) throw new IllegalArgumentException("O preço deve ser maior que 0.");

        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
