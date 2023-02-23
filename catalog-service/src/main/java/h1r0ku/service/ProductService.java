package h1r0ku.service;

import h1r0ku.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product create(Product product);
    Page<Product> getAll(Pageable pageable);
    Product getById(Long productId);
}
