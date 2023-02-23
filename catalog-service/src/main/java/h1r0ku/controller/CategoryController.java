package h1r0ku.controller;

import h1r0ku.dto.request.CategoryRequest;
import h1r0ku.dto.response.CategoryResponse;
import h1r0ku.dto.response.ProductResponse;
import h1r0ku.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalog/categories")
public class CategoryController {

    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(categoryMapper.getCategories());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryMapper.createCategory(categoryRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryMapper.getById(id));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<Page<ProductResponse>> getProductsByCategoryId(@PathVariable("id") Long id, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(categoryMapper.getProductsByCategoryId(id, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryMapper.updateCategory(id, categoryRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        categoryMapper.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
