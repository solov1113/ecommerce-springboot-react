package h1r0ku.controller;

import h1r0ku.dto.request.CustomerRequest;
import h1r0ku.dto.request.ShippingAddressRequest;
import h1r0ku.dto.response.CustomerResponse;
import h1r0ku.dto.response.ShippingAddressResponse;
import h1r0ku.mapper.CustomerMapper;
import h1r0ku.mapper.ShippingAddressMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer Controller")
public class CustomerController {

    private final CustomerMapper customerMapper;
    private final ShippingAddressMapper shippingAddressMapper;

    @PostMapping
    @Operation(summary = "Create a new customer", description = "Create a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer created"),
            @ApiResponse(responseCode = "400", description = "Invalid customer data"),
            @ApiResponse(responseCode = "409", description = "Customer already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<CustomerResponse> registration(@Valid @RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok(customerMapper.registration(customerRequest));
    }

    @PostMapping("/{customerId}/upload")
    @Operation(summary = "Upload a profile picture", description = "Upload a profile picture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Picture uploaded"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<CustomerResponse> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(customerMapper.uploadImage(image, customerId));
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

//    Shipping Address
    @PostMapping("/shipping-address")
    @Operation(summary = "Create a new shipping address", description = "Create a new shipping address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shipping address created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ShippingAddressResponse> createShippingAddress(@Valid @RequestBody ShippingAddressRequest address) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shippingAddressMapper.createShippingAddress(address));
    }

    @GetMapping("/shipping-address/{id}")
    @Operation(summary = "Get a shipping address by ID", description = "Get a shipping address by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shipping address retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Shipping address not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ShippingAddressResponse> getShippingAddressById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(shippingAddressMapper.getShippingAddressById(id));
    }

    @GetMapping("/{id}/shipping-address/")
    @Operation(summary = "Get all shipping addresses by customer ID", description = "Get all shipping addresses by customer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shipping addresses retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ShippingAddressResponse>> getAllShippingAddressesByCustomerId(@PathVariable("id") Long customerId) {
        return ResponseEntity.ok(shippingAddressMapper.getAllShippingAddressesByCustomerId(customerId));
    }

    @PutMapping("/shipping-address/{id}")
    @Operation(summary = "Update a shipping address by ID", description = "Update a shipping address by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shipping address updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Shipping address not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ShippingAddressResponse> updateShippingAddress(@PathVariable("id") Long id,
                                                                 @Valid @RequestBody ShippingAddressRequest address) {
        return ResponseEntity.ok(shippingAddressMapper.updateShippingAddress(id, address));
    }

    @DeleteMapping("/shipping-address/{id}")
    @Operation(summary = "Delete a shipping address by ID", description = "Delete a shipping address by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Shipping address deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Shipping address not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteShippingAddress(@PathVariable("id") Long id) {
        shippingAddressMapper.deleteShippingAddress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
