package catalogservice.dto.request;

import catalogservice.dto.response.CategoryResponse;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ProductRequest {
    private Double price;
    private Long categoryId;
}
