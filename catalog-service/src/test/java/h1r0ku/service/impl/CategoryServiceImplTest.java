package h1r0ku.service.impl;

import h1r0ku.entity.Banner;
import h1r0ku.entity.Category;
import h1r0ku.entity.Product;
import h1r0ku.exceptions.CategoryLevelException;
import h1r0ku.feign.ImageClient;
import h1r0ku.repository.BannerRepository;
import h1r0ku.repository.CategoryRepository;
import h1r0ku.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BannerRepository bannerRepository;

    @Mock
    private ImageClient imageClient;

    private CategoryServiceImpl serviceTest;

    @BeforeEach
    public void setUp() {
        serviceTest = new CategoryServiceImpl(categoryRepository, productRepository, bannerRepository, imageClient);
    }

    @Test
    public void create_shouldSaveCategory() {
        Category category = new Category();
        category.setName("Test Category");
        // Given
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // When
        Category savedCategory = serviceTest.create(category);

        // Then
        assertNotNull(savedCategory);
        assertEquals(category, savedCategory);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void getAll_shouldReturnAllCategories() {
        List<Category> categories = new ArrayList<>(List.of(new Category(), new Category()));

        // Given
        when(categoryRepository.findAll()).thenReturn(categories);

        // When
        List<Category> allCategories = serviceTest.getAll();

        // Then
        assertNotNull(allCategories);
        assertEquals(categories.size(), allCategories.size());
        assertTrue(allCategories.containsAll(categories));
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getCategoryById_shouldReturnCategory() {
        // Given
        Category category = new Category();
        category.setId(1L);

        // When
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        Category result = serviceTest.getCategoryById(1L);

        // Then
        verify(categoryRepository).findById(1L);
        assertEquals(category, result);
    }

    @Test
    void getProductsByCategory_shouldReturnProductPage() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> productList = new ArrayList<>(List.of(new Product(), new Product()));
        Page<Product> productPage = new PageImpl<>(productList);

        // When
        when(productRepository.findAllByCategory_Id(1L, pageable)).thenReturn(productPage);
        Page<Product> result = serviceTest.getProductsByCategory(1L, pageable);

        // Then
        verify(productRepository).findAllByCategory_Id(1L, pageable);
        assertEquals(productPage, result);
    }

    @Test
    void setParentCategory_shouldThrowExceptionWhenParentCategoryIsLevel3() {
        // Given
        Category parentCategory = new Category();
        parentCategory.setLevel(3);
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(parentCategory));

        Category category = new Category();
        category.setParentCategory(parentCategory);

        // When and Then
        assertThrows(CategoryLevelException.class, () -> {
            serviceTest.setParentCategory(1L, category);
        });
    }

    @Test
    void setParentCategory_shouldSetCategoryParentCategoryAndLevel() {
        // Given
        Category parentCategory = new Category();
        parentCategory.setLevel(2);
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(parentCategory));

        Category category = new Category();

        // When
        Category resultCategory = serviceTest.setParentCategory(1L, category);

        // Then
        assertEquals(parentCategory, resultCategory.getParentCategory());
        assertEquals(parentCategory.getLevel() + 1, resultCategory.getLevel());
    }

    @Test
    void updateCategory_shouldUpdateCategoryAndSave() {
        // Given
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");

        Category parentCategory = new Category();
        parentCategory.setId(2L);
        parentCategory.setName("Category 2");
        parentCategory.setLevel(1);

        category.setParentCategory(parentCategory);

        Mockito.when(categoryRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.findById(Mockito.eq(2L))).thenReturn(Optional.of(parentCategory));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        // When
        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Category 1");
        Category resultCategory = serviceTest.updateCategory(1L, 2L, updatedCategory);

        // Then
        assertEquals("Updated Category 1", resultCategory.getName());
        assertEquals(parentCategory, resultCategory.getParentCategory());
        Mockito.verify(categoryRepository, Mockito.times(2)).save(Mockito.any());
    }

    @Test
    void updateCategory_shouldRemoveChildFromOldParentCategory() {
        // Given
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");

        Category parentCategory = new Category();
        parentCategory.setId(2L);
        parentCategory.setName("Category 2");
        parentCategory.setLevel(1);

        Category oldParentCategory = new Category();
        oldParentCategory.setId(3L);
        oldParentCategory.setName("Category 3");
        oldParentCategory.setLevel(1);
        oldParentCategory.getChildCategories().add(category);

        category.setParentCategory(oldParentCategory);

        Mockito.when(categoryRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.findById(Mockito.eq(2L))).thenReturn(Optional.of(parentCategory));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(parentCategory);

        // When
        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Category 1");
        Category resultCategory = serviceTest.updateCategory(1L, 2L, updatedCategory);

        // Then
        Mockito.verify(categoryRepository, Mockito.times(2)).save(Mockito.any());
    }

    @Test
    void deleteCategory_shouldDeleteCategoryById() {
        // Given
        Long categoryId = 1L;

        // When
        serviceTest.deleteCategory(categoryId);

        // Then
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(Mockito.eq(categoryId));
    }

    @Test
    void uploadBanners_shouldUploadBannersAndReturnCategory() {
        // Given
        Category category = new Category();
        category.setId(1L);
        category.setLevel(1);
        MultipartFile[] banners = { mock(MultipartFile.class), mock(MultipartFile.class) };
        List<Banner> bannerList = new ArrayList<>();
        bannerList.add(new Banner("file1.jpg", category));
        bannerList.add(new Banner("file2.jpg", category));
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(imageClient.uploadImage(any(MultipartFile.class))).thenReturn("file1.jpg", "file2.jpg");
        when(bannerRepository.save(any(Banner.class))).thenAnswer(invocation -> {
            Banner banner = invocation.getArgument(0);
            banner.setId(1L);
            return banner;
        });
        // When
        Category result = serviceTest.uploadBanners(category.getId(), banners);

        // Then
        verify(categoryRepository, times(1)).findById(category.getId());
        verify(imageClient, times(2)).uploadImage(any(MultipartFile.class));
        verify(bannerRepository, times(2)).save(any(Banner.class));
        assertEquals(2, result.getBanners().size());
        assertEquals(bannerList.get(0).getSrc(), result.getBanners().get(0).getSrc());
        assertEquals(bannerList.get(1).getSrc(), result.getBanners().get(1).getSrc());
    }
}