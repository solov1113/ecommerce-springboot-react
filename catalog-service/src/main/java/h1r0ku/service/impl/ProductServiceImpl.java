package h1r0ku.service.impl;

import h1r0ku.entity.Category;
import h1r0ku.entity.Product;
import h1r0ku.entity.ProductImage;
import h1r0ku.exceptions.NotFoundException;
import h1r0ku.feign.ImageClient;
import h1r0ku.repository.ProductImageRepository;
import h1r0ku.repository.ProductRepository;
import h1r0ku.service.CategoryService;
import h1r0ku.service.ProductService;
import h1r0ku.utils.UpdatingUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final int MIN_REVIEWS = 10;
    private static final int NORMALIZATION_FACTOR = 100;

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CategoryService categoryService;
    private final ImageClient imageClient;

    private static double calculatePopularityScore(Product product) {
        Float avgRating = product.getAverageStar();
        Integer numReviews = product.getReviews().size();

        double score = (MIN_REVIEWS / (MIN_REVIEWS + (float)numReviews)) * avgRating
                + (numReviews / (MIN_REVIEWS + (float)numReviews)) * NORMALIZATION_FACTOR;

        return score;
    }

    private void uploadProductImage(List<MultipartFile> images, Product product) {
        if(images.size() > 0) {
            for(MultipartFile image : images) {
                String fileSrc = imageClient.uploadImage(image);
                productImageRepository.save(new ProductImage(fileSrc, product));
            }
        }
    }

    @Override
    public Product create(Product product, List<MultipartFile> images, Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        product.setCategory(category);
        Product saved = productRepository.save(product);
        uploadProductImage(images, saved);
        return saved;
    }

    @Override
    public Product update(Long productId, Product product, List<MultipartFile> images, Long categoryId) {
        Product p = getById(productId);

        Category category = categoryService.getCategoryById(categoryId);
        Category oldCategory = p.getCategory();
        oldCategory.removeChild(productId);

        BeanUtils.copyProperties(product, p, UpdatingUtil.getNullPropertyNames(p));
        p.setCategory(category);

        categoryService.updateCategory(oldCategory.getId(), oldCategory.getParentCategory().getId(), oldCategory);
        return productRepository.save(p);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateOrderCount(Long productId, boolean increase) {
        productRepository.updateOrderCount(productId, increase);
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
    }
}
