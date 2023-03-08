package h1r0ku.service.impl;

import h1r0ku.dto.catalog.product.ProductResponse;
import h1r0ku.dto.request.WishlistRequest;
import h1r0ku.entity.Customer;
import h1r0ku.entity.Wishlist;
import h1r0ku.feign.ProductClient;
import h1r0ku.repository.WishlistRepository;
import h1r0ku.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistServiceImplTest {

    @Mock
    private WishlistRepository wishlistRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private ProductClient productClient;

    private WishlistServiceImpl serviceTest;

    @BeforeEach
    void setUp() {
        serviceTest = new WishlistServiceImpl(wishlistRepository, customerService, productClient);
    }

    @Test
    void create_shouldCreateWishlist() {
        // Given
        WishlistRequest wishlistRequest = new WishlistRequest();
        wishlistRequest.setCustomerId(1L);
        wishlistRequest.setProductId(2L);
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerService.getById(1L)).thenReturn(customer);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(2L);
        ResponseEntity<ProductResponse> responseEntity = new ResponseEntity<>(productResponse, HttpStatus.OK);
        when(productClient.getProductById(2L)).thenReturn(responseEntity);

        Wishlist wishlist = new Wishlist();
        wishlist.setId(1L);
        wishlist.setCustomer(customer);
        wishlist.setProductId(2L);
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);

        // When
        Wishlist createdWishlist = serviceTest.create(wishlistRequest);

        // Then
        assertNotNull(createdWishlist);
        assertEquals(customer, createdWishlist.getCustomer());
        assertEquals(2L, createdWishlist.getProductId());
    }

    @Test
    void getAllByCustomerId_shouldReturnWishlistsByCustomerId() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Wishlist> expectedPage = new PageImpl<>(Collections.emptyList());
        when(wishlistRepository.findAllByCustomer_Id(1L, pageable)).thenReturn(expectedPage);

        // When
        Page<Wishlist> actualPage = serviceTest.getAllByCustomerId(1L, pageable);

        // Then
        assertNotNull(actualPage);
        assertEquals(expectedPage, actualPage);
        verify(wishlistRepository).findAllByCustomer_Id(1L, pageable);
    }

    @Test
    void delete_shouldDeleteWishlistById() {
        // Given
        Long wishlistId = 1L;
        doNothing().when(wishlistRepository).deleteById(wishlistId);

        // When
        serviceTest.delete(wishlistId);

        // Then
        verify(wishlistRepository).deleteById(wishlistId);
    }
}