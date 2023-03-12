package h1r0ku.service.impl;

import h1r0ku.entity.Product;
import h1r0ku.entity.Review;
import h1r0ku.feign.CustomerClient;
import h1r0ku.repository.ReviewRepository;
import h1r0ku.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private CustomerClient customerClient;
    @Mock
    private ProductService productService;

    private ReviewServiceImpl serviceTest;

    @BeforeEach
    public void setup() {
        serviceTest = new ReviewServiceImpl(
                reviewRepository,
                customerClient,
                productService
        );
    }

    @Test
    void create_givenValidProductAndReview_shouldCreateReview() {
        // Given
        Long productId = 1L;
        Long customerId = 2L;
        Product product = new Product();
        product.setId(productId);

        List<Review> reviews = new ArrayList<>();
        Review existingReview = new Review();
        existingReview.setRating((short) 4);
        existingReview.setProduct(product);
        reviews.add(existingReview);
        product.setReviews(reviews);

        Review review = new Review();
        review.setCustomerId(customerId);
        review.setRating((short) 5);

        when(productService.getById(productId)).thenReturn(product);
        when(customerClient.getById(customerId)).thenReturn(ResponseEntity.ok(new Object()));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // When
        Review result = serviceTest.create(productId, review);

        // Then
        verify(customerClient, times(1)).getById(customerId);
        verify(productService, times(1)).getById(productId);
        verify(productService, times(1)).update(product);
        verify(reviewRepository, times(1)).save(any(Review.class));

        assertEquals(result, review);
        assertEquals(product.getAverageStar(), 4.5f);
        assertTrue(product.getReviews().contains(review));
    }

    @Test
    void update_givenValidReviewIdAndProduct_shouldUpdateReview() {
        // Given
        Long productId = 1L;
        Long reviewId = 2L;
        Review existingReview = new Review();
        existingReview.setRating((short) 4);
        existingReview.setProduct(new Product());
        existingReview.setId(reviewId);
        Review newReview = new Review();
        newReview.setRating((short) 3);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
        when(productService.getById(productId)).thenReturn(existingReview.getProduct());
        when(reviewRepository.save(any(Review.class))).thenReturn(newReview);

        // When
        Review result = serviceTest.update(reviewId, productId, newReview);

        // Then
        verify(productService, times(1)).getById(productId);
        verify(reviewRepository, times(1)).findById(reviewId);
        verify(reviewRepository, times(1)).save(any(Review.class));
        assertEquals(result.getRating(), newReview.getRating());
    }

    @Test
    void getById_givenValidReviewId_shouldReturnReview() {
        // Given
        Long reviewId = 1L;
        Review review = new Review();
        review.setId(reviewId);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        // When
        Review result = serviceTest.getById(reviewId);

        // Then
        verify(reviewRepository, times(1)).findById(reviewId);
        assertEquals(result, review);
    }

    @Test
    public void getByProductId_shouldReturnPageOfReviews() {
        // Given
        Long productId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        List<Review> reviews = new ArrayList<>();
        Product product = new Product();
        product.setId(productId);
        for (int i = 1; i <= 10; i++) {
            Review review = new Review();
            review.setId((long) i);
            review.setProduct(product);
            reviews.add(review);
        }

        Page<Review> expectedPage = new PageImpl<>(reviews, pageable, reviews.size());

        when(reviewRepository.findByProduct_Id(productId, pageable)).thenReturn(expectedPage);

        // When
        Page<Review> result = serviceTest.getByProductId(productId, pageable);

        // Then
        assertEquals(result, expectedPage);
        verify(reviewRepository).findByProduct_Id(productId, pageable);
    }

    @Test
    public void getByCustomerId_shouldReturnPageOfReviews() {
        // Given
        Long customerId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        List<Review> reviews = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Review review = new Review();
            review.setId((long) i);
            review.setCustomerId(customerId);
            reviews.add(review);
        }

        Page<Review> expectedPage = new PageImpl<>(reviews, pageable, reviews.size());

        when(reviewRepository.findByCustomerId(customerId, pageable)).thenReturn(expectedPage);

        // When
        Page<Review> result = serviceTest.getByCustomerId(customerId, pageable);

        // Then
        assertEquals(result, expectedPage);
        verify(reviewRepository).findByCustomerId(customerId, pageable);
    }

    @Test
    public void deleteById_shouldDeleteReviewById() {
        // Given
        Long reviewId = 1L;

        // When
        serviceTest.deleteById(reviewId);

        // Then
        verify(reviewRepository).deleteById(reviewId);
    }
}