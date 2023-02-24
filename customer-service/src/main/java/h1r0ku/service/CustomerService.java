package h1r0ku.service;

import h1r0ku.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    Customer registration(Customer customer);
    Page<Customer> getAll(Pageable pageable);
    Customer getById(Long id);
    Customer getByUsername(String username);
    void deleteById(Long id);
}
