package h1r0ku.service;

import h1r0ku.entity.Category;
import h1r0ku.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    List<Category> getAll();
    Category getCategoryById(Long id);
    Page<Product> getProductsByCategory(Long categoryId, Pageable pageable);
    Category setParentCategory(Long parentId, Category category);
    Category updateCategory(Long id, Long parentCategoryId, Category updatedCategory);
    Category updateCategory(Long id, Category updatedCategory);
    void deleteCategory(Long id);
    void deleteBanner(Long id);
    Category uploadBanners(Long id, MultipartFile[] banners);
}
