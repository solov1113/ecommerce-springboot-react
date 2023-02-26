package h1r0ku.dto.catalog.category;

import h1r0ku.dto.catalog.product.ProductResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private Set<CategoryResponse> childCategories = new HashSet<>();
    private CategoryResponse parentCategory;
    private Set<ProductResponse> products = new HashSet<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
