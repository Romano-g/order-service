package com.romano.orders.order_service.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID orderId;
    private OrderStatus status;
    private final List<OrderItem> items = new ArrayList<OrderItem>();

    public Order(UUID orderId) {
        if (orderId == null) throw new IllegalArgumentException("O orderId não pode ser null.");

        this.orderId = orderId;
        this.status = OrderStatus.CREATED;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }

    public void addItem(OrderItem item) {
        if (item == null) throw new IllegalArgumentException("Item não deve ser null");
        if (this.status != OrderStatus.CREATED) throw new IllegalStateException("Só é possível adicionar itens a pedidos com status CREATED.");

        this.items.add(item);
    }

    public void removeItem(OrderItem item) {
        if (item == null) throw new IllegalArgumentException("Item não deve ser null");
        if (this.status != OrderStatus.CREATED) throw new IllegalStateException("Só é possível remover itens a pedidos com status CREATED.");

        boolean removed = this.items.remove(item);

        if (!removed) throw new IllegalArgumentException("Não foi possível remover o item pois ele não faz parte desse pedido.");
    }

    public void confirm() {
        if (this.status != OrderStatus.CREATED) throw new IllegalStateException(
                "Só é possível confirmar pedidos com o status em CREATED."
        );

        if (this.items.isEmpty()) throw new IllegalStateException(
                "Não é possível confirmar pedidos sem itens."
        );

        this.status = OrderStatus.CONFIRMED;
    }

    public void cancel() {
        if (this.status == OrderStatus.CANCELLED) throw new IllegalStateException("O pedido já está cancelado.");

        this.status = OrderStatus.CANCELLED;
    }

    public BigDecimal getTotal() {
        return items.stream()
                .map(OrderItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
