package h1r0ku.service.impl;

import h1r0ku.entity.Customer;
import h1r0ku.entity.ShippingAddress;
import h1r0ku.repository.ShippingAddressServiceRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShippingAddressServiceImplTest {

    @Mock
    private ShippingAddressServiceRepository shippingAddressServiceRepository;
    @Mock
    private CustomerServiceImpl customerService;

    private ShippingAddressServiceImpl serviceTest;

    @BeforeEach
    void setUp() {
        serviceTest = new ShippingAddressServiceImpl(shippingAddressServiceRepository, customerService);
    }

    @Test
    void createShippingAddress_shouldCreateShippingAddress() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(1L);
        shippingAddress.setCustomer(customer);
        when(customerService.getById(1L)).thenReturn(customer);
        when(shippingAddressServiceRepository.save(any())).thenReturn(shippingAddress);

        // When
        ShippingAddress result = serviceTest.createShippingAddress(1L, shippingAddress);

        // Then
        assertEquals(shippingAddress.getCustomer(), result.getCustomer());
        assertEquals(shippingAddress.getId(), result.getId());
        verify(shippingAddressServiceRepository, times(1)).save(any());
        verify(customerService, times(1)).getById(1L);
    }

    @Test
    void getShippingAddressById_shouldReturnShippingAddress() {
        // Given
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(1L);
        when(shippingAddressServiceRepository.findById(1L)).thenReturn(Optional.of(shippingAddress));

        // When
        ShippingAddress result = serviceTest.getShippingAddressById(1L);

        // Then
        assertEquals(shippingAddress.getCustomer(), result.getCustomer());
        assertEquals(shippingAddress.getFirstName(), result.getFirstName());
        assertEquals(shippingAddress.getLastName(), result.getLastName());
        verify(shippingAddressServiceRepository, times(1)).findById(1L);
    }

    @Test
    void getAllShippingAddressesByCustomerId_shouldReturnShippingAddresses() {
        // Given
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(1L);
        List<ShippingAddress> shippingAddresses = Collections.singletonList(shippingAddress);
        when(shippingAddressServiceRepository.findAllByCustomer_Id(1L)).thenReturn(shippingAddresses);

        // When
        List<ShippingAddress> result = serviceTest.getAllShippingAddressesByCustomerId(1L);

        // Then
        assertEquals(shippingAddresses, result);
        verify(shippingAddressServiceRepository, times(1)).findAllByCustomer_Id(1L);
    }

    @Test
    void updateShippingAddress_shouldUpdateShippingAddress() {
        // Given
        ShippingAddress existingAddress = new ShippingAddress();
        existingAddress.setId(1L);
        existingAddress.setFirstName("Adam");
        Customer customer = new Customer();
        customer.setId(1L);
        existingAddress.setCustomer(customer);
        when(shippingAddressServiceRepository.findById(1L)).thenReturn(Optional.of(existingAddress));
        ShippingAddress updatedAddress = new ShippingAddress();
        updatedAddress.setId(1L);
        updatedAddress.setFirstName("John");
        when(shippingAddressServiceRepository.save(any(ShippingAddress.class))).thenReturn(updatedAddress);
        // When
        ShippingAddress result = serviceTest.updateShippingAddress(1L, updatedAddress);

        // Then
        assertEquals(updatedAddress.getId(), result.getId());
        assertEquals(updatedAddress.getFirstName(), result.getFirstName());
    }

    @Test
    void deleteShippingAddress_shouldDeleteShippingAddress() {
        // When
        serviceTest.deleteShippingAddress(1L);

        // Then
        verify(shippingAddressServiceRepository).deleteById(1L);
    }
}