package h1r0ku.service.impl;

import h1r0ku.entity.OrderItem;
import h1r0ku.feign.ProductClient;
import h1r0ku.repository.OrderItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceImplTest {

    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private ProductClient productClient;

    private OrderItemServiceImpl serviceTest;

    @BeforeEach
    void setUp() {
        serviceTest = new OrderItemServiceImpl(orderItemRepository, productClient);
    }

    @Test
    void create_shouldCallRepositorySaveAndProductClientUpdateOrdersCount() {
        // Given
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(1L);

        // When
        serviceTest.create(orderItem);

        // Then
        verify(orderItemRepository).save(orderItem);
        verify(productClient).updateOrdersCount(orderItem.getProductId(), true);
    }

    @Test
    void updateOrderItem_shouldCallRepositorySave() {
        // Given
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setProductId(1L);

        OrderItem updatedOrderItem = new OrderItem();
        updatedOrderItem.setId(1L);
        updatedOrderItem.setProductId(2L);

        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));

        // When
        serviceTest.updateOrderItem(1L, updatedOrderItem);

        // Then
        verify(orderItemRepository).save(orderItem);
    }

    @Test
    void deleteOrderItem_shouldCallRepositoryDeleteById() {
        // Given
        Long id = 1L;

        // When
        serviceTest.deleteOrderItem(id);

        // Then
        verify(orderItemRepository).deleteById(id);
    }
}