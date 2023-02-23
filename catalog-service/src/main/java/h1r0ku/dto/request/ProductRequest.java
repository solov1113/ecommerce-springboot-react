package h1r0ku.dto.request;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRequest {
    private Double price;
    private Long categoryId;
}
