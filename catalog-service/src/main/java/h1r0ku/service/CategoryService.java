package h1r0ku.service;

import h1r0ku.entity.Category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    List<Category> getAll();
}
