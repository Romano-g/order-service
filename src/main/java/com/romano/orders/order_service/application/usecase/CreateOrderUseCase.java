package com.romano.orders.order_service.application.usecase;

import com.romano.orders.order_service.application.exception.ProductNotActiveException;
import com.romano.orders.order_service.application.exception.ProductNotFoundException;
import com.romano.orders.order_service.application.port.OrderRepository;
import com.romano.orders.order_service.application.port.ProductRepository;
import com.romano.orders.order_service.domain.model.Order;
import com.romano.orders.order_service.domain.model.OrderItem;
import com.romano.orders.order_service.domain.model.Product;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.UUID;

public class CreateOrderUseCase {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final Validator validator;

    public CreateOrderUseCase(ProductRepository productRepository, OrderRepository orderRepository, Validator validator) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.validator = validator;
    }

    public Order execute(CreateOrderCommand command) {
        var violations = validator.validate(command);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);

        var order = new Order(UUID.randomUUID());
        var itemsToAdd = command.getItems();

        for (CreateOrderItemCommand item : itemsToAdd) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(String.format("Produto %s não encontrado.", item.getProductId())));

            var orderItem = getOrderItem(item, product);

            order.addItem(orderItem);
        }

        return orderRepository.save(order);
    }

    private static OrderItem getOrderItem(CreateOrderItemCommand item, Product product) {
        if (!product.isActive()) throw new ProductNotActiveException(
                String.format("Produto %s não está ativo.", item.getProductId())
        );

        return new OrderItem(
                product.getId(), item.getQuantity(), product.getPrice()
        );
    }
}
