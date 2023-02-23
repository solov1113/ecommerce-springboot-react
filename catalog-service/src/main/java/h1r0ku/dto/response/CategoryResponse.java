package h1r0ku.dto.response;

import h1r0ku.entity.Category;
import h1r0ku.entity.Product;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private Set<Category> childCategories = new HashSet<>();
    private Category parentCategory;
    private Set<Product> products = new HashSet<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
