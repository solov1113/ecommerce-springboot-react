package catalogservice.service;

import catalogservice.entity.Category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    List<Category> getAll();
}
