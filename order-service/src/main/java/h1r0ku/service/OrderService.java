package h1r0ku.service;

import h1r0ku.entity.Order;
import h1r0ku.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order create(Order order);
    Page<Order> getAll(Pageable pageable);
    Order getOrderById(Long id);
    Page<OrderItem> getOrderItemsByOrder(Long orderId, Pageable pageable);
    Page<Order> getOrdersByUser(Long userId, Pageable pageable);
    Order updateOrder(Long id, Order updatedOrder);
    void deleteOrder(Long id);
}
