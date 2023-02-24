package h1r0ku.dto.response;

import h1r0ku.entity.Order;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderItemResponse {
    private Long id;
    private Integer quantity;
    private Long productId;
    private Order order;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
