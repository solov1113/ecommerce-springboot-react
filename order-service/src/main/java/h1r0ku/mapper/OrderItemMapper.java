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
        Order updatedOrder = orderService.addItem(orderId, orderItem, product.getPrice(), productId);
        return basicMapper.convertTo(updatedOrder, OrderResponse.class);
    }

    public OrderItemResponse updateOrderItem(Long id, OrderItemRequest updatedOrderItem) {
        OrderItem orderItem = basicMapper.convertTo(updatedOrderItem, OrderItem.class);
        return basicMapper.convertTo(orderItemService.updateOrderItem(id, orderItem), OrderItemResponse.class);
    }

    public void deleteOrderItem(Long id) {
        orderItemService.deleteOrderItem(id);
    }
}
