package h1r0ku.controller;

import h1r0ku.dto.request.ProductRequest;
import h1r0ku.dto.catalog.product.ProductResponse;
import h1r0ku.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalog/products")
@Tag(name = "Product Controller")
public class ProductController {

    private final ProductMapper productMapper;

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

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Create a new product", description = "Creates a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created product"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<ProductResponse> createProduct(@ModelAttribute ProductRequest productRequest) {
        return ResponseEntity.ok(productMapper.createProduct(productRequest));
    }
}
