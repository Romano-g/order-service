package com.romano.orders.order_service.application.usecase;

import com.romano.orders.order_service.application.exception.ProductNotActiveException;
import com.romano.orders.order_service.application.exception.ProductNotFoundException;
import com.romano.orders.order_service.application.port.OrderRepository;
import com.romano.orders.order_service.application.port.ProductRepository;
import com.romano.orders.order_service.domain.model.Order;
import com.romano.orders.order_service.domain.model.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Validator validator;

    private CreateOrderUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateOrderUseCase(
                productRepository,
                orderRepository,
                validator
        );
    }

    @Test
    void shouldCreateAndSaveOrderWhenProductsAreActive() {
        // Arrange
        Product product = new Product(
                "Teclado",
                new BigDecimal("100.00")
        );
        product.activate();

        var command = new CreateOrderCommand(
                List.of(new CreateOrderItemCommand(product.getId(), 2))
        );

        when(validator.validate(command)).thenReturn(Set.of());
        when(productRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Order result = useCase.execute(command);

        // Assert
        assertEquals(1, result.getItems().size());
        assertEquals(new BigDecimal("200.00"), result.getTotal());

        verify(productRepository).findById(product.getId());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void shouldThrowWhenProductDoesNotExist() {
        // Arrange
        UUID productId = UUID.randomUUID();

        var command = new CreateOrderCommand(
                List.of(new CreateOrderItemCommand(productId, 1))
        );

        when(validator.validate(command)).thenReturn(Set.of());
        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ProductNotFoundException.class,
                () -> useCase.execute(command)
        );

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void shouldThrowWhenProductIsInactive() {
        // Arrange
        Product product = new Product(
                "Teclado",
                new BigDecimal("100.00")
        );
        product.deactivate();

        var command = new CreateOrderCommand(
                List.of(new CreateOrderItemCommand(product.getId(), 1))
        );

        when(validator.validate(command)).thenReturn(Set.of());
        when(productRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));

        // Act + Assert
        assertThrows(
                ProductNotActiveException.class,
                () -> useCase.execute(command)
        );

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void shouldRejectInvalidCommandBeforeSearchingProducts() {
        // Arrange
        var command = new CreateOrderCommand(List.of());

        @SuppressWarnings("unchecked")
        ConstraintViolation<CreateOrderCommand> violation =
                mock(ConstraintViolation.class);

        when(validator.validate(command)).thenReturn(Set.of(violation));

        // Act + Assert
        assertThrows(
                ConstraintViolationException.class,
                () -> useCase.execute(command)
        );

        verifyNoInteractions(productRepository, orderRepository);
    }
}
