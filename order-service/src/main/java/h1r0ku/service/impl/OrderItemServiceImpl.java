package h1r0ku.service.impl;

import h1r0ku.entity.OrderItem;
import h1r0ku.repository.OrderItemRepository;
import h1r0ku.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem create(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem updateOrderItem(Long id, OrderItem updatedOrderItem) {
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow();
        orderItem.setQuantity(updatedOrderItem.getQuantity());
        orderItem.setOrder(updatedOrderItem.getOrder());
        orderItem.setProductId(updatedOrderItem.getProductId());
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}
