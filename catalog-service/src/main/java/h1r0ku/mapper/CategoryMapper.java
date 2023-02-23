package h1r0ku.mapper;

import h1r0ku.dto.request.CategoryRequest;
import h1r0ku.dto.response.CategoryResponse;
import h1r0ku.entity.Category;
import h1r0ku.service.CategoryService;
import lombok.RequiredArgsConstructor;
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
}
