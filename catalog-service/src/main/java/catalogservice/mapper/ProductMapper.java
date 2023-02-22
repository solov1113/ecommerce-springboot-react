package catalogservice.mapper;

import catalogservice.dto.request.ProductRequest;
import catalogservice.dto.response.ProductResponse;
import catalogservice.entity.Product;
import catalogservice.service.ProductService;
import commons.mapper.BasicMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final BasicMapper basicMapper;
    private final ProductService productService;

    public Page<ProductResponse> getProducts(Pageable pageable) {
        return productService.getAll(pageable).map(product -> basicMapper.convertTo(product, ProductResponse.class));
    }

    public ProductResponse getProductById(Long productId) {
        return basicMapper.convertTo(productService.getById(productId), ProductResponse.class);
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productService.create(basicMapper.convertTo(productRequest, Product.class));
        return basicMapper.convertTo(product, ProductResponse.class);
    }
}
