package h1r0ku.dto.catalog.product;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductRequest {
    @Size(min = 3, max = 40, message = "Product name must be between 3 and 40 characters")
    @NotBlank(message = "Product name value mustn't be null or whitespace")
    private String productName;

    @Size(min = 20, max = 500, message = "Description must be between 20 and 500 characters")
    @NotBlank(message = "Description value mustn't be null or whitespace")
    private String description;

    @DecimalMin(value = "0.00", message = "Price must be greater than or equal to 0.00")
    @DecimalMax(value = "99999999.99", message =  "Price must be less than or equal to 99 999 999.99")
    @NotNull(message = "Price is required")
    private BigDecimal price;

    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    @Max(value = 10_000_000, message = "Quantity must be less than or equal to 10 000 000")
    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

//    @DecimalMin(value = "0.00", message = "Average star must be greater than or equal to 0.00")
//    @DecimalMax(value = "5.00", message =  "Average star must be less than or equal to 5.00")
//    @NotNull(message = "Average star is required")
//    private Float averageStar;
//
//    @PositiveOrZero(message = "Orders count must be positive or equal to zero")
//    @NotNull(message = "Orders count is required")
//    private Integer ordersCount;
}
