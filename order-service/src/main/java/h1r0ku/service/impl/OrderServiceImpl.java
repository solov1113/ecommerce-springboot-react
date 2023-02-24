package h1r0ku.service.impl;

import h1r0ku.entity.Order;
import h1r0ku.entity.OrderItem;
import h1r0ku.repository.OrderItemRepository;
import h1r0ku.repository.OrderRepository;
import h1r0ku.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Override
    public Page<OrderItem> getOrderItemsByOrder(Long orderId, Pageable pageable) {
        return orderItemRepository.findByOrder_Id(orderId, pageable);
    }

    @Override
    public Page<Order> getOrdersByUser(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Order updateOrder(Long id, Order updatedOrder) {
        Order order = getOrderById(id);
        order.setOrderDescription(updatedOrder.getOrderDescription());
        order.setOrderItems(updatedOrder.getOrderItems());
        order.setOrderFee(updatedOrder.getOrderFee());
        order.setOrderFee(updatedOrder.getOrderFee());
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
