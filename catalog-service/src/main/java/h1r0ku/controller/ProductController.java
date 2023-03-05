package h1r0ku.controller;

import h1r0ku.dto.catalog.product.ProductRequest;
import h1r0ku.dto.catalog.product.ProductResponse;
import h1r0ku.mapper.ProductMapper;
import h1r0ku.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalog/products")
@Tag(name = "Product Controller")
public class ProductController {

    private final ProductMapper productMapper;
    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get a paginated list of products", description = "Returns a paginated list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of products"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<ProductResponse>> getProducts(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(productMapper.getProducts(pageable));
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get a product by ID", description = "Returns a single product identified by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productMapper.getProductById(productId));
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created product"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productMapper.createProduct(productRequest));
    }

    @PostMapping("/{productId}/upload")
    @Operation(summary = "Upload new images for a product", description = "Upload new images for a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully uploaded images"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<ProductResponse> uploadImage(@RequestParam("images") MultipartFile[] images, @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productMapper.uploadImages(images, productId));
    }

    @PutMapping("/{productId}/orders/count/{increase}")
    @Operation(summary = "Update orders count in product", description = "Update orders count in product by product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated orders count"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public void updateOrderCount(@PathVariable("productId") Long productId, @PathVariable("increase") boolean increase) {
        productService.updateOrderCount(productId, increase);
    }


    @PutMapping(value = "/{productId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Update product", description = "Update a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("productId") Long productId,
                                                         @Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productMapper.updateProduct(productId, productRequest));
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete a product by id", description = "Delete a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) {
        productMapper.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}