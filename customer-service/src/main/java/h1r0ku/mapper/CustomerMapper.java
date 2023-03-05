package h1r0ku.mapper;

import h1r0ku.dto.request.CustomerRequest;
import h1r0ku.dto.response.CustomerResponse;
import h1r0ku.entity.Customer;
import h1r0ku.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class CustomerMapper {

    private final BasicMapper basicMapper;
    private final CustomerService customerService;

    public CustomerResponse registration(CustomerRequest customerRequest) {
        Customer customer = basicMapper.convertTo(customerRequest, Customer.class);
        return basicMapper.convertTo(customerService.registration(customer), CustomerResponse.class);
    }

    public CustomerResponse uploadImage(MultipartFile image, Long customerId) {
        return basicMapper.convertTo(customerService.uploadImage(image, customerId), CustomerResponse.class);
    }

    public Page<CustomerResponse> getAll(Pageable pageable) {
        return customerService.getAll(pageable).map(customer -> basicMapper.convertTo(customer, CustomerResponse.class));
    }
    public CustomerResponse getById(Long id) {
        return basicMapper.convertTo(customerService.getById(id), CustomerResponse.class);
    }
    public CustomerResponse getByUsername(String username) {
        return basicMapper.convertTo(customerService.getByUsername(username), CustomerResponse.class);
    }
    public void deleteById(Long id) {
        customerService.deleteById(id);
    }
}
