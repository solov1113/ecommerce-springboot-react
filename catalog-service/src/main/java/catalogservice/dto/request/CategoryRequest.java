package catalogservice.dto.request;

import catalogservice.entity.Category;

import java.util.HashSet;
import java.util.Set;

public class CategoryRequest {
    private String name;
    private Set<Category> childCategories = new HashSet<>();
    private Category parentCategory;
}
