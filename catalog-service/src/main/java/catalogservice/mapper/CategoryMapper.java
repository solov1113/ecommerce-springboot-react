package catalogservice.mapper;

import catalogservice.dto.request.CategoryRequest;
import catalogservice.dto.response.CategoryResponse;
import catalogservice.entity.Category;
import catalogservice.service.CategoryService;
import commons.mapper.BasicMapper;
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
