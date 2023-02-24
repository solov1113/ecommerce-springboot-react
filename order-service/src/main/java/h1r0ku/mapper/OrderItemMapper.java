package h1r0ku.mapper;

import h1r0ku.dto.request.OrderItemRequest;
import h1r0ku.dto.response.OrderItemResponse;
import h1r0ku.entity.Order;
import h1r0ku.entity.OrderItem;
import h1r0ku.service.OrderItemService;
import h1r0ku.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemMapper {

    private final BasicMapper basicMapper;
    private final OrderItemService orderItemService;
    private final OrderService orderService;

    public OrderItemResponse create(OrderItemRequest orderItemRequest) {
        OrderItem orderItem = basicMapper.convertTo(orderItemRequest, OrderItem.class);
        Order order = orderService.getOrderById(orderItemRequest.getOrderId());
        orderItem.setOrder(order);
        return basicMapper.convertTo(orderItemService.create(orderItem), OrderItemResponse.class);
    }
    public OrderItemResponse updateOrderItem(Long id, OrderItemRequest updatedOrderItem) {
        OrderItem orderItem = basicMapper.convertTo(updatedOrderItem, OrderItem.class);
        return basicMapper.convertTo(orderItemService.updateOrderItem(id, orderItem), OrderItemResponse.class);
    }

    void deleteOrderItem(Long id) {
        orderItemService.deleteOrderItem(id);
    }
}
