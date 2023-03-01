package h1r0ku.service.impl;

import h1r0ku.dto.catalog.product.ProductResponse;
import h1r0ku.entity.Order;
import h1r0ku.entity.OrderItem;
import h1r0ku.enums.OrderStatus;
import h1r0ku.exceptions.ProductQuantityUnavailableException;
import h1r0ku.feign.ProductClient;
import h1r0ku.repository.OrderItemRepository;
import h1r0ku.repository.OrderRepository;
import h1r0ku.service.OrderItemService;
import h1r0ku.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderItemService orderItemService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductClient productClient;

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
        order.setOrderStatus(updatedOrder.getOrderStatus());
        return orderRepository.save(order);
    }

    @Override
    public Order addItem(Long orderId, OrderItem orderItem, BigDecimal productPrice, Long productId) {
        Order order = getOrderById(orderId);
        ProductResponse productResponse = productClient.getProductById(productId).getBody();

        if(productResponse.getQuantity() < orderItem.getQuantity()) {
            throw new ProductQuantityUnavailableException("Not enough " + productResponse.getProductName() + " available");
        }

        Integer productQty = productResponse.getQuantity();
        Integer orderItemQty = orderItem.getQuantity();

        orderItem.setOrder(order);
        order.increaseOrderFee(productPrice.multiply(BigDecimal.valueOf(orderItemQty)));

        Optional<OrderItem> existedItem = order.getOrderItems()
                .stream()
                .filter(oi -> oi.getProductId().equals(productId))
                .findFirst();
        if(existedItem.isPresent()) {
            OrderItem item = existedItem.get();
            item.setQuantity(item.getQuantity() + orderItemQty);
            orderItemService.updateOrderItem(item.getId(), item);
        } else {
            orderItemService.create(orderItem);
        }
        productResponse.setQuantity(productQty - orderItemQty);

        return create(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
