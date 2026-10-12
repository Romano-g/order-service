package com.romano.orders.order_service.application.port;

import com.romano.orders.order_service.domain.model.Order;

public interface OrderRepository {
    Order save(Order order);
}
