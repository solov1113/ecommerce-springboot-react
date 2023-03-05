package h1r0ku.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemRequest {
    @Min(value = 1, message = "Quantity must be greater than or equal to 1")
    @Max(value = 10_000_000, message = "Quantity must be less than or equal to 10 000 000")
    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "Product ID is required")
    private Long productId;
}