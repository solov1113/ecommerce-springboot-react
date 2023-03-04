package h1r0ku.dto.request;

import h1r0ku.dto.cart.CartResponse;
import h1r0ku.enums.OrderStatus;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderRequest extends CartResponse {
    @Size(min = 3, max = 25, message = "Order description must be between 3 and 100 characters")
    private String orderDescription;
    private OrderStatus orderStatus;
}
