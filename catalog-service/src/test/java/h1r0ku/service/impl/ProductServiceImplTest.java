package h1r0ku.service.impl;

import h1r0ku.entity.Category;
import h1r0ku.entity.Product;
import h1r0ku.entity.ProductImage;
import h1r0ku.feign.ImageClient;
import h1r0ku.repository.ProductImageRepository;
import h1r0ku.repository.ProductRepository;
import h1r0ku.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductImageRepository productImageRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private ImageClient imageClient;

    private ProductServiceImpl serviceTest;

    private static final int MIN_REVIEWS = 10;
    private static final int NORMALIZATION_FACTOR = 100;

    @BeforeEach
    public void setup() {
        serviceTest = new ProductServiceImpl(
                productRepository,
                productImageRepository,
                categoryService,
                imageClient
        );
    }

    @Test
    public void create_shouldCreateProductWithGivenCategoryId() {
        // Given
        Product product = new Product();
        product.setProductName("Product");
        product.setDescription("Product description");
        Long categoryId = 1L;

        Category category = new Category();
        category.setId(categoryId);

        when(categoryService.getCategoryById(categoryId)).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        Product createdProduct = serviceTest.create(product, categoryId);

        // Then
        assertNotNull(createdProduct);
        assertEquals(category, createdProduct.getCategory());
    }

    @Test
    public void update_shouldUpdateProductWithGivenIdAndCategoryId() {
        // Given


        Long categoryId = 2L;

        Category oldCategory = new Category();
        Set<Product> products = oldCategory.getProducts();
        oldCategory.setId(1L);
        oldCategory.setProducts(products);

        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setProductName("Product");
        existingProduct.setDescription("Product description");
        existingProduct.setCategory(oldCategory);

        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated product");
        updatedProduct.setDescription("Updated product description");

        Category category = new Category();
        category.setId(categoryId);

        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(existingProduct));
        when(categoryService.getCategoryById(categoryId)).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // When
        Product result = serviceTest.update(productId, updatedProduct, categoryId);

        // Then
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getDescription(), result.getDescription());
        assertEquals(category, result.getCategory());
    }

    @Test
    void update_shouldUpdateGivenProduct() {
        // Given
        Long productId = 1L;
        Long categoryId = 2L;
        Product product = new Product();
        product.setProductName("Product");
        product.setId(productId);
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Category");
        product.setCategory(category);

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setProductName("Existing Product");
        existingProduct.setCategory(category);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(categoryService.getCategoryById(categoryId)).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        Product updatedProduct = serviceTest.update(productId, product, categoryId);

        // Then
        verify(productRepository, times(1)).findById(productId);
        verify(categoryService, times(1)).getCategoryById(categoryId);
        verify(productRepository, times(1)).save(any(Product.class));

        assertNotNull(updatedProduct);
        assertEquals(updatedProduct.getId(), productId);
        assertEquals(updatedProduct.getProductName(), product.getProductName());
        assertEquals(updatedProduct.getCategory(), product.getCategory());
        assertEquals(updatedProduct.getCategory().getId(), product.getCategory().getId());
    }

    @Test
    public void uploadImages_shouldUploadProductImages() {
        // Given
        Long productId = 1L;
        MultipartFile[] images = new MultipartFile[] {
                new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image".getBytes()),
                new MockMultipartFile("image", "test2.jpg", "image/jpeg", "test2 image".getBytes())
        };
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));
        when(productImageRepository.save(any(ProductImage.class))).thenReturn(new ProductImage());

        // When
        Product result = serviceTest.uploadImages(images, productId);

        // Then
        assertEquals(result.getImages().size(), images.length);
    }

    @Test
    public void updateOrderCount_shouldUpdateOrderCount() {
        // Given
        Long productId = 1L;

        // When
        serviceTest.updateOrderCount(productId, true);

        // Then
        verify(productRepository).updateOrderCount(productId, true);
    }

    private static Float calculatePopularityScore(Product product) {
        Float avgRating = product.getAverageStar();
        int numReviews = product.getReviews().size();

        return (MIN_REVIEWS / (MIN_REVIEWS + (float)numReviews)) * avgRating
                + (numReviews / (MIN_REVIEWS + (float)numReviews)) * NORMALIZATION_FACTOR;
    }

    @Test
    public void getAll_shouldReturnAllProductsSortedByPopularity() {
        // Given
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());

        Comparator<Product> comparator = (p1, p2) -> {
            Float p1Score = calculatePopularityScore(p1);
            Float p2Score = calculatePopularityScore(p2);
            return p2Score.compareTo(p1Score);
        };
        List<Product> sortedProducts = products.stream().sorted(comparator).toList();
        when(productRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(sortedProducts));
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("popularity")));

        // When
        Page<Product> result = serviceTest.getAll(pageable);

        // Then
        assertEquals(result.getContent(), sortedProducts);
    }

    @Test
    public void getAll_shouldReturnAllProducts() {
        // Given
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        when(productRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(products));

        // When
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> result = serviceTest.getAll(pageable);

        // Then
        assertEquals(result.getContent(), products);

        // Also check that the sort object is empty
        Sort sort = result.getSort();
        assertTrue(sort.isEmpty());
    }

    @Test
    public void getById_shouldReturnProductById() {
        // Given
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));

        // When
        Product result = serviceTest.getById(productId);

        // Then
        assertEquals(result, product);
    }

    @Test
    public void deleteProduct_shouldDeleteProductById() {
        // Given
        Long productId = 1L;

        // When
        serviceTest.deleteProduct(productId);

        // Then
        verify(productRepository).deleteById(productId);
    }
}