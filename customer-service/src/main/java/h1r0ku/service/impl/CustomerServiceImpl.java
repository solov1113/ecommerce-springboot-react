package h1r0ku.service.impl;

import h1r0ku.dto.authentication.AuthenticationRequest;
import h1r0ku.dto.cart.CartRequest;
import h1r0ku.entity.Customer;
import h1r0ku.exceptions.NotFoundException;
import h1r0ku.feign.AuthClient;
import h1r0ku.feign.CartClient;
import h1r0ku.feign.ImageClient;
import h1r0ku.repository.CustomerRepository;
import h1r0ku.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ImageClient imageClient;
    private final CustomerRepository customerRepository;
    private final AuthClient authClient;
    private final CartClient cartClient;

    @Override
    public Customer registration(Customer customer, MultipartFile image) {
        String password = customer.getPassword();
        customer.setPassword(new BCryptPasswordEncoder().encode(password));
        if(image != null && !image.isEmpty()) {
            String imageUrl = imageClient.uploadImage(image);
            customer.setImageUrl(imageUrl);
        }
        Customer c = customerRepository.save(customer);
        authClient.authenticate(new AuthenticationRequest(customer.getUsername(), password));
        cartClient.createCart(new CartRequest(c.getId()));
        return c;
    }

    @Override
    public Page<Customer> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    @Override
    public Customer getByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
