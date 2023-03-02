package h1r0ku.dto.response;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponse {
    private Long id;
    private Long customerId;
    private String text;
    private Short rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
