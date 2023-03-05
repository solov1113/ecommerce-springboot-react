package h1r0ku.service;

import h1r0ku.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerService {
    Customer registration(Customer customer);
    Customer uploadImage(MultipartFile image, Long customerId);
    Page<Customer> getAll(Pageable pageable);
    Customer getById(Long id);
    Customer getByUsername(String username);
    void deleteById(Long id);
}
