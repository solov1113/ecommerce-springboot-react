package h1r0ku.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemRequest {
    @Positive
    @NotNull
    @Min(value = 0)
    private Integer quantity;
    private Long productId;
}
