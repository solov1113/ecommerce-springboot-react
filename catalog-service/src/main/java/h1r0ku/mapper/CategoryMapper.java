package h1r0ku.mapper;

import h1r0ku.dto.request.CategoryRequest;
import h1r0ku.dto.response.CategoryResponse;
import h1r0ku.dto.response.ProductResponse;
import h1r0ku.entity.Category;
import h1r0ku.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final BasicMapper basicMapper;
    private final CategoryService categoryService;

    public List<CategoryResponse> getCategories() {
        return basicMapper.convertListTo(categoryService.getAll(), CategoryResponse.class);
    }

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = categoryService.create(basicMapper.convertTo(categoryRequest, Category.class));
        return basicMapper.convertTo(category, CategoryResponse.class);
    }

    public CategoryResponse getById(Long id) {
        return basicMapper.convertTo(categoryService.getCategoryById(id), CategoryResponse.class);
    }

    public Page<ProductResponse> getProductsByCategoryId(Long id, Pageable pageable) {
        return categoryService.getProductsByCategory(id, pageable).map(product -> basicMapper.convertTo(product, ProductResponse.class));
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryService.updateCategory(id, basicMapper.convertTo(categoryRequest, Category.class));
        return basicMapper.convertTo(category, CategoryResponse.class);
    }

    public void deleteCategory(Long id) {
        categoryService.deleteCategory(id);
    }
}
