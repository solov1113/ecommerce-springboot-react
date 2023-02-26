package h1r0ku.dto.response;

import h1r0ku.entity.OrderItem;
import h1r0ku.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private Long customerId;
    private String orderDescription;
    private BigDecimal orderFee;
    private OrderStatus orderStatus;
    private List<OrderItem> orderItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
