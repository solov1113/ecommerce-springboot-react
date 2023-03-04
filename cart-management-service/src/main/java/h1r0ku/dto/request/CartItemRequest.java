package h1r0ku.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemRequest {
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
