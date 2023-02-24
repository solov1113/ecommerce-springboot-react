package h1r0ku.dto.request;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Integer quantity;
    private Long productId;
    private Long orderId;
}
