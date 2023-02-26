package h1r0ku.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderItemResponse {
    private Long id;
    private Integer quantity;
    private Long productId;
    @JsonIgnoreProperties("orderItems")
    private OrderResponse order;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
