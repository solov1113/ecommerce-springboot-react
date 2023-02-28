package h1r0ku.mapper;

import h1r0ku.dto.catalog.product.ProductResponse;
import h1r0ku.dto.request.OrderItemRequest;
import h1r0ku.dto.response.OrderItemResponse;
import h1r0ku.dto.response.OrderResponse;
import h1r0ku.entity.Order;
import h1r0ku.entity.OrderItem;
import h1r0ku.feign.ProductClient;
import h1r0ku.service.OrderItemService;
import h1r0ku.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderItemMapper {

    private final BasicMapper basicMapper;
    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final ProductClient productClient;

    public OrderResponse create(Long orderId, OrderItemRequest orderItemRequest) {
        Long productId = orderItemRequest.getProductId();
        ProductResponse product = productClient.getProductById(productId).getBody();

        OrderItem orderItem = basicMapper.convertTo(orderItemRequest, OrderItem.class);
        Order order = orderService.getOrderById(orderId);
        orderItem.setOrder(order);
        order.increaseOrderFee(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity())));

        Optional<OrderItem> existedItem = order.getOrderItems().stream().filter(oi -> oi.equals(productId)).findFirst();
        if(existedItem.isPresent()) {
            OrderItem item = existedItem.get();
            item.setQuantity(item.getQuantity() + orderItem.getQuantity());
            orderItemService.updateOrderItem(item.getId(), item);
        } else {
            orderItemService.create(orderItem);
        }



        Order updatedOrder = orderService.updateOrder(orderId, order);
        return basicMapper.convertTo(updatedOrder, OrderResponse.class);
    }

    public OrderItemResponse updateOrderItem(Long id, OrderItemRequest updatedOrderItem) {
        OrderItem orderItem = basicMapper.convertTo(updatedOrderItem, OrderItem.class);
        return basicMapper.convertTo(orderItemService.updateOrderItem(id, orderItem), OrderItemResponse.class);
    }

    void deleteOrderItem(Long id) {
        orderItemService.deleteOrderItem(id);
    }
}
