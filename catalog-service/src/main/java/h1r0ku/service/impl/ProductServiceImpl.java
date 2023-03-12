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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
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

    private static Float calculatePopularityScore(Product product) {
        Float avgRating = product.getAverageStar();
        int numReviews = product.getReviews().size();

        return (MIN_REVIEWS / (MIN_REVIEWS + (float)numReviews)) * avgRating
                + (numReviews / (MIN_REVIEWS + (float)numReviews)) * NORMALIZATION_FACTOR;
    }

    private Comparator<Product> getProductComparator() {
        return (p1, p2) -> {
            Float p1Score = calculatePopularityScore(p1);
            Float p2Score = calculatePopularityScore(p2);
            return p2Score.compareTo(p1Score);
        };
    }

    private List<ProductImage> uploadProductImage(MultipartFile[] images, Product product) {
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile image : images) {
            String fileSrc = imageClient.uploadImage(image);
            ProductImage productImage = productImageRepository.save(new ProductImage(fileSrc, product));
            productImages.add(productImage);
        }
        return productImages;
    }

    @Override
    public Product create(Product product, Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Product update(Long productId, Product product, Long categoryId) {
        Product p = getById(productId);

        Category category = categoryService.getCategoryById(categoryId);

        Category oldCategory = p.getCategory();
        oldCategory.removeChild(productId);

        BeanUtils.copyProperties(product, p, UpdatingUtil.getNullPropertyNames(p));
        p.setCategory(category);

        return productRepository.save(p);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product uploadImages(MultipartFile[] images, Long productId) {
        Product product = getById(productId);
        product.setImages(uploadProductImage(images, product));
        return product;
    }

    @Override
    @Transactional
    public void updateOrderCount(Long productId, boolean increase) {
        productRepository.updateOrderCount(productId, increase);
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {
        Sort sort = pageable.getSort();
        if(!sort.isEmpty() &&
           sort.iterator().next().getProperty().equals("popularity")
        ) {
            List<Product> products = productRepository.findAll(pageable).stream()
                    .sorted(getProductComparator())
                    .toList();
            return new PageImpl<>(products, pageable, products.size());
        }
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
