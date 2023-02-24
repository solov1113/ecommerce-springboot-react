package h1r0ku.dto.request;

import h1r0ku.dto.response.OrderItemResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequest {
    private Long cartId;
    private String orderDescription;
    private BigDecimal orderFee;
    private List<OrderItemResponse> orderItems;
}
