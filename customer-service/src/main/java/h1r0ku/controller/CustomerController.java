package h1r0ku.controller;

import h1r0ku.dto.request.CustomerRequest;
import h1r0ku.dto.response.CustomerResponse;
import h1r0ku.mapper.CustomerMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Customer Controller")
public class CustomerController {

    private final CustomerMapper customerMapper;

    @PostMapping
    @Operation(summary = "Create a new customer", description = "Create a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created"),
            @ApiResponse(responseCode = "400", description = "Invalid customer data"),
            @ApiResponse(responseCode = "409", description = "Customer already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<CustomerResponse> registration(@RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok(customerMapper.registration(customerRequest));
    }

    @GetMapping
    @Operation(summary = "Get a paginated list of customers", description = "Returns a paginated list of all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of customers"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<Page<CustomerResponse>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(customerMapper.getAll(pageable));
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Get a customer by ID", description = "Returns a single customer identified by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<CustomerResponse> getById(@PathVariable("customerId") Long id) {
        return ResponseEntity.ok(customerMapper.getById(id));
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Get a customer by username", description = "Returns a single customer identified by its username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<CustomerResponse> getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(customerMapper.getByUsername(username));
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Delete a customer by id", description = "Delete a customer by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<Void> deleteById(@PathVariable("customerId") Long id) {
        customerMapper.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
