package h1r0ku.service.impl;

import h1r0ku.dto.authentication.AuthenticationRequest;
import h1r0ku.dto.cart.CartRequest;
import h1r0ku.entity.Customer;
import h1r0ku.exceptions.AlreadyExistException;
import h1r0ku.exceptions.NotFoundException;
import h1r0ku.feign.AuthClient;
import h1r0ku.feign.CartClient;
import h1r0ku.feign.ImageClient;
import h1r0ku.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ImageClient imageClient;
    @Mock
    private AuthClient authClient;
    @Mock
    private CartClient cartClient;

    private CustomerServiceImpl serviceTest;

    @BeforeEach
    void setUp() {
        serviceTest = new CustomerServiceImpl(customerRepository, imageClient, authClient, cartClient);
    }

    @Test
    void registration_shouldRegisterCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setUsername("john");
        customer.setPassword("password");

        when(customerRepository.findByUsername(anyString())).thenReturn(null);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // When
        Customer result = serviceTest.registration(customer);

        // Then
        verify(customerRepository).findByUsername("john");
        verify(customerRepository).save(customer);
        verify(authClient).authenticate(any(AuthenticationRequest.class));
        verify(cartClient).createCart(any(CartRequest.class));
        assertTrue(result.getPassword().startsWith("$2a$"));
    }

    @Test
    void registration_shouldThrowAlreadyExistExceptionIfUsernameIsTaken() {
        // Given
        Customer customer = new Customer();
        customer.setUsername("john");
        customer.setPassword("password");

        when(customerRepository.findByUsername(anyString())).thenReturn(new Customer());

        // When, Then
        assertThrows(AlreadyExistException.class, () -> serviceTest.registration(customer));
    }

    @Test
    void uploadImage_shouldUploadImage() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image".getBytes());
        String imageUrl = "http://example.com/test.jpg";

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(customer));
        when(imageClient.uploadImage(any())).thenReturn(imageUrl);

        // When
        Customer result = serviceTest.uploadImage(image, 1L);

        // Then
        verify(customerRepository).findById(1L);
        verify(imageClient).uploadImage(image);
        assertEquals(imageUrl, result.getImageUrl());
    }

    @Test
    void uploadImage_shouldNotUploadImageIfFileIsEmpty() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        MockMultipartFile image = new MockMultipartFile("image", "", "image/jpeg", new byte[0]);

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(customer));

        // When
        Customer result = serviceTest.uploadImage(image, 1L);

        // Then
        verify(customerRepository).findById(1L);
        verifyNoInteractions(imageClient);
        assertNull(result.getImageUrl());
    }

    @Test
    void getAll_shouldReturnAllCustomers() {
        // Given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        Customer customer2 = new Customer();
        customer2.setId(2L);
        Page<Customer> page = new PageImpl<>(List.of(customer1, customer2));
        Pageable pageable = Pageable.unpaged();

        when(customerRepository.findAll(pageable)).thenReturn(page);

        // When
        Page<Customer> result = serviceTest.getAll(pageable);

        // Then
        verify(customerRepository).findAll(pageable);
        assertEquals(2, result.getTotalElements());
        assertTrue(result.getContent().contains(customer1));
        assertTrue(result.getContent().contains(customer2));
    }

    @Test
    void getById_shouldReturnCustomerById() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(customer));

        // When
        Customer result = serviceTest.getById(1L);

        // Then
        verify(customerRepository).findById(1L);
        assertEquals(customer, result);
    }

    @Test
    void getById_shouldThrowNotFoundExceptionIfCustomerNotFound() {
        // Given
        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        // When, Then
        assertThrows(NotFoundException.class, () -> serviceTest.getById(1L));
    }

    @Test
    void getByUsername_shouldReturnCustomerByUsername() {
        // Given
        Customer customer = new Customer();
        customer.setUsername("john");

        when(customerRepository.findByUsername(anyString())).thenReturn(customer);

        // When
        Customer result = serviceTest.getByUsername("john");

        // Then
        verify(customerRepository).findByUsername("john");
        assertEquals(customer, result);
    }

    @Test
    void deleteById_shouldDeleteCustomerById() {
        // Given, When
        serviceTest.deleteById(1L);

        // Then
        verify(customerRepository).deleteById(1L);
    }
}