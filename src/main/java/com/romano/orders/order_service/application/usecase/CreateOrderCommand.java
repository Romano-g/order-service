package com.romano.orders.order_service.application.usecase;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateOrderCommand {
    @NotEmpty
    private final List<@NotNull @Valid CreateOrderItemCommand> items;

    public CreateOrderCommand(List<CreateOrderItemCommand> items) {
        if (items == null) throw new IllegalArgumentException("Itens não pode ser null");

        this.items = List.copyOf(items);
    }

    public List<CreateOrderItemCommand> getItems() {
        return items;
    }
}
