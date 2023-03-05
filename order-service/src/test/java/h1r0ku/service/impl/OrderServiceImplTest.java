package h1r0ku.service.impl;

import h1r0ku.entity.Order;
import h1r0ku.entity.OrderItem;
import h1r0ku.repository.OrderItemRepository;
import h1r0ku.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;

    private OrderServiceImpl serviceTest;

    @BeforeEach
    void setUp() {
        serviceTest = new OrderServiceImpl(orderRepository, orderItemRepository);
    }

    @Test
    void create() {
        // Given
        Order order = new Order();
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // When
        Order result = serviceTest.create(order);

        // Then
        verify(orderRepository, times(1)).save(any(Order.class));
        assertThat(result).isEqualTo(order);
    }

    @Test
    void getAll() {
        // Given
        Pageable pageable = Pageable.unpaged();
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        Page<Order> page = new PageImpl<>(orders);
        when(orderRepository.findAll(pageable)).thenReturn(page);

        // When
        Page<Order> result = serviceTest.getAll(pageable);

        // Then
        verify(orderRepository, times(1)).findAll(pageable);
        assertThat(result).isEqualTo(page);
    }

    @Test
    void getOrderById() {
        // Given
        Long id = 1L;
        Order order = new Order();
        order.setId(id);
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        // When
        Order result = serviceTest.getOrderById(id);

        // Then
        verify(orderRepository, times(1)).findById(id);
        assertThat(result).isEqualTo(order);
    }

    @Test
    void getOrderItemsByOrder() {
        // Given
        Long orderId = 1L;
        Pageable pageable = Pageable.unpaged();
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem());
        Page<OrderItem> page = new PageImpl<>(orderItems);
        when(orderItemRepository.findByOrder_Id(orderId, pageable)).thenReturn(page);

        // When
        Page<OrderItem> result = serviceTest.getOrderItemsByOrder(orderId, pageable);

        // Then
        verify(orderItemRepository, times(1)).findByOrder_Id(orderId, pageable);
        assertThat(result).isEqualTo(page);
    }

    @Test
    void getOrdersByCustomer() {
        // Given
        Long customerId = 1L;
        Pageable pageable = Pageable.unpaged();
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        Page<Order> page = new PageImpl<>(orders);
        when(orderRepository.findByCustomerId(customerId, pageable)).thenReturn(page);

        // When
        Page<Order> result = serviceTest.getOrdersByCustomer(customerId, pageable);

        // Then
        verify(orderRepository, times(1)).findByCustomerId(customerId, pageable);
        assertThat(result).isEqualTo(page);
    }
}