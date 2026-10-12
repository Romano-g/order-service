package com.romano.orders.order_service.application.exception;

public class ProductNotActiveException extends RuntimeException {
    public ProductNotActiveException (String message) {
        super(message);
    }
}
