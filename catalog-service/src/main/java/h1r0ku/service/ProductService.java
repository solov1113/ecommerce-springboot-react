package h1r0ku.service;

import h1r0ku.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Product create(Product product, List<MultipartFile> images, Long categoryId);
    Product update(Long productId, Product product, List<MultipartFile> images, Long categoryId);
    Page<Product> getAll(Pageable pageable);
    Product getById(Long productId);
}
