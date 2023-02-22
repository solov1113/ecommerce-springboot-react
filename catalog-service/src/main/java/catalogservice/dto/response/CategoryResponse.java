package catalogservice.dto.response;

import catalogservice.entity.Category;
import catalogservice.entity.Product;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private Set<Category> childCategories = new HashSet<>();
    private Category parentCategory;
    private Set<Product> products = new HashSet<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
