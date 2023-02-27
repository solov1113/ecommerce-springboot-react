package h1r0ku.service.impl;

import h1r0ku.entity.Category;
import h1r0ku.entity.Product;
import h1r0ku.exceptions.CategoryLevelException;
import h1r0ku.repository.CategoryRepository;
import h1r0ku.repository.ProductRepository;
import h1r0ku.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Category not found"));
    }

    @Override
    public Page<Product> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findAllByCategory_Id(categoryId, pageable);
    }

    @Override
    public Category setParentCategory(Long parentId, Category category) {
        if(parentId != null) {
            Category parentCategory = getCategoryById(parentId);
            category.setParentCategory(parentCategory);
            if(parentCategory.getLevel() == 3) {
                throw new CategoryLevelException("You cannot choose this category as parent because his level is 3");
            }
            category.setLevel(parentCategory.getLevel() + 1);
        } else {
            category.setLevel(1);
        }
        return category;
    }

    @Override
    public Category updateCategory(Long id, Long parentCategoryId, Category updatedCategory) {
        Category category = getCategoryById(id);
        category.setName(updatedCategory.getName());
        setParentCategory(parentCategoryId, category);

//      Remove child from old parent category
        Category oldParent = category.getParentCategory().removeChild(category.getId());
        categoryRepository.save(oldParent);

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
