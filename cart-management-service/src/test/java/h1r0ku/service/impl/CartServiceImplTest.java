package h1r0ku.service.impl;

import h1r0ku.entity.Cart;
import h1r0ku.feign.OrderClient;
import h1r0ku.feign.ProductClient;
import h1r0ku.mapper.BasicMapper;
import h1r0ku.repository.CartItemRepository;
import h1r0ku.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private BasicMapper basicMapper;
    @Mock
    private OrderClient orderClient;
    @Mock
    private ProductClient productClient;

    private CartServiceImpl serviceTest;

    @BeforeEach
    void setUp() {
        serviceTest = new CartServiceImpl(cartRepository, cartItemRepository, basicMapper, orderClient, productClient);
    }

    @Test
    void create() {
        // Given
        Long id = 1L;
        Cart cart = new Cart();
        cart.setCustomerId(id);

        // When
        serviceTest.create(cart);

        // Then
        verify(cartRepository).save(cart);
    }

    @Test
    void addItem() {
    }

    @Test
    void removeItem() {
    }

    @Test
    void getById() {
    }

    @Test
    void completeCart() {
    }

    @Test
    void getByCustomerId() {
    }

    @Test
    void deleteByCustomerId() {
    }
}