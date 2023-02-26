package h1r0ku.dto.request;

import h1r0ku.enums.OrderStatus;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {
    private Long customerId;
    @Size(min = 3, max = 25, message = "Order description must be between 3 and 100 characters")
    private String orderDescription;
    private OrderStatus orderStatus = OrderStatus.NOT_PROCESSED;
    private BigDecimal orderFee = new BigDecimal(0);
}
