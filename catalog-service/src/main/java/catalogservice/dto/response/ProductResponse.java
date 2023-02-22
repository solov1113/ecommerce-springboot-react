package catalogservice.dto.response;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ProductResponse {
    private Long id;
    private Double price;
    private CategoryResponse category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
