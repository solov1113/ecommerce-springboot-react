package h1r0ku.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReviewRequest {
    private Long id;
    @NotNull(message = "CustomerId value couldn't be")
    private Long customerId;
    private String text;
    @NotNull(message = "CustomerId value couldn't be")
    private Long productId;
    @Positive
    @Min(value = 1)
    @Max(value = 5)
    @NotNull
    private Short rating;
}
