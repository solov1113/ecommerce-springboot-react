package h1r0ku.service.impl;

import h1r0ku.entity.Product;
import h1r0ku.entity.ProductImage;
import h1r0ku.feign.ImageClient;
import h1r0ku.repository.ProductImageRepository;
import h1r0ku.repository.ProductRepository;
import h1r0ku.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ImageClient imageClient;

    @Override
    public Product create(Product product, List<MultipartFile> images) {
        Product saved = productRepository.save(product);
        if(images.size() > 0) {
            for(MultipartFile image : images) {
                String fileSrc = imageClient.uploadImage(image);
                productImageRepository.save(new ProductImage(fileSrc, saved));
            }
        }
        return saved;
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }
}
