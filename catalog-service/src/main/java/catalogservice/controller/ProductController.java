package catalogservice.controller;

import catalogservice.dto.request.ProductRequest;
import catalogservice.dto.response.ProductResponse;
import catalogservice.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalog/products")
public class ProductController {

    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(productMapper.getProducts(pageable));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productMapper.getProductById(productId));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productMapper.createProduct(productRequest));
    }
}
