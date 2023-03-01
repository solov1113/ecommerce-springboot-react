package h1r0ku.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemRequest {
    @Positive
    private Integer quantity;
    private Long productId;
}
