package h1r0ku.dto.request;

import lombok.Data;

@Data
public class ProductRequest {
    private Double price;
    private Long categoryId;
}
