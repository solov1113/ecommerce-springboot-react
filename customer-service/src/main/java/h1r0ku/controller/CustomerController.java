package h1r0ku.controller;

import h1r0ku.dto.request.CustomerRequest;
import h1r0ku.dto.response.CustomerResponse;
import h1r0ku.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<CustomerResponse> registration(@RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok(customerMapper.registration(customerRequest));
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(customerMapper.getAll(pageable));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable("customerId") Long id) {
        return ResponseEntity.ok(customerMapper.getById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<CustomerResponse> getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(customerMapper.getByUsername(username));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteById(@PathVariable("customerId") Long id) {
        customerMapper.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
