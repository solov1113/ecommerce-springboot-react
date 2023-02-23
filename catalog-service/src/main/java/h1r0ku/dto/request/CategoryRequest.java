package h1r0ku.dto.request;

import h1r0ku.entity.Category;

import java.util.HashSet;
import java.util.Set;

public class CategoryRequest {
    private String name;
    private Set<Category> childCategories = new HashSet<>();
    private Category parentCategory;
}
