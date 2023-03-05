package h1r0ku.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartRequest {
    @NotNull(message = "Customer ID is required")
    private Long customerId;
}
