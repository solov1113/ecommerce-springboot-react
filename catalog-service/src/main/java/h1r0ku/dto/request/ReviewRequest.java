package h1r0ku.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReviewRequest {
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @Size(min = 10, max = 150, message = "Product name must be between 1 and 150 characters")
    private String text;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @Min(value = 1, message = "Rating must be greater than or equal to 1")
    @Max(value = 5, message = "Rating must be less than or equal to 5")
    @NotNull(message = "Rating is required")
    private Short rating;
}