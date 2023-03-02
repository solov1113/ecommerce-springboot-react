package h1r0ku.service;

import h1r0ku.entity.Order;
import h1r0ku.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order create(Order order);
    Page<Order> getAll(Pageable pageable);
    Order getOrderById(Long id);
    Page<OrderItem> getOrderItemsByOrder(Long orderId, Pageable pageable);
    Page<Order> getOrdersByCustomer(Long customerId, Pageable pageable);
    Order updateOrder(Long orderId, Order updatedOrder);
    Order addItem(Long orderId, OrderItem orderItem, BigDecimal productPrice, Long productId);
    void deleteOrder(Long id);
}
