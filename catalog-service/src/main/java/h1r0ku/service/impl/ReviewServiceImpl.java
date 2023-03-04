package h1r0ku.service.impl;

import h1r0ku.entity.Product;
import h1r0ku.entity.Review;
import h1r0ku.exceptions.NotFoundException;
import h1r0ku.feign.CustomerClient;
import h1r0ku.repository.ReviewRepository;
import h1r0ku.service.ProductService;
import h1r0ku.service.ReviewService;
import h1r0ku.utils.UpdatingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerClient customerClient;
    private final ProductService productService;

    @Override
    public Review create(Long productId, Review review) {
        customerClient.getById(review.getCustomerId()); // If customer doesn't exist feign will throw an exception
        Product product = productService.getById(productId);
        int sum = product.getReviews().stream().mapToInt(Review::getRating).sum() + review.getRating();
        product.setAverageStar((float)Math.round((double) sum / product.getReviews().size()));
        review.setProduct(product);
        productService.update(product);
        return reviewRepository.save(review);
    }

    @Override
    public Review update(Long reviewId, Long productId, Review review) {
        Review oldReview = getById(reviewId);
        BeanUtils.copyProperties(review, oldReview, UpdatingUtil.getNullPropertyNames(review));
        return create(productId, oldReview);
    }

    @Override
    public Review getById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("Review not found"));
    }

    @Override
    public Page<Review> getByProductId(Long productId, Pageable pageable) {
        return reviewRepository.findByProduct_Id(productId, pageable);
    }

    @Override
    public Page<Review> getByCustomerId(Long customerId, Pageable pageable) {
        return reviewRepository.findByCustomerId(customerId, pageable);
    }

    @Override
    public void deleteById(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
