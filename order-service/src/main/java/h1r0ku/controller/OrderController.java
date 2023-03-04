package h1r0ku.controller;

import h1r0ku.dto.request.OrderRequest;
import h1r0ku.dto.response.OrderItemResponse;
import h1r0ku.dto.response.OrderResponse;
import h1r0ku.mapper.OrderItemMapper;
import h1r0ku.mapper.OrderMapper;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "Order Controller")
public class OrderController {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @PostMapping
    @Operation(summary = "Create order", description = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderMapper.create(orderRequest));
    }

    @GetMapping
    @Operation(summary = "Get a paginated list of order", description = "Returns a paginated list of all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All orders retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<OrderResponse>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(orderMapper.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an order by ID", description = "Returns a single order identified by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderMapper.getOrderById(id));
    }

    @GetMapping("/{id}/items")
    @Operation(summary = "Get Order items by Order ID", description = "Retrieve all order items belonging to a order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order items retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<OrderItemResponse>> getOrderItemsByOrder(@PathVariable("id") Long id,
                                                                        @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(orderMapper.getOrderItemsByOrder(id, pageable));
    }

    @GetMapping("/customer/{id}")
    @Operation(summary = "Get Orders by Customer ID", description = "Retrieve all orders belonging to a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<OrderResponse>> getOrdersByUser(@PathVariable("id") Long id,
                                                               @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(orderMapper.getOrdersByCustomer(id, pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update order by ID", description = "Update a category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable("id") Long id,
                                                     @Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderMapper.updateOrder(id, orderRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order by id", description = "Delete an order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order deleted"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        orderMapper.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//  Order items

    @DeleteMapping("/items/{id}")
    @Operation(summary = "Delete an order item by id", description = "Delete an order item by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order item deleted"),
            @ApiResponse(responseCode = "404", description = "Order item not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<Void> deleteOrderItem(@PathVariable("id") Long id) {
        orderItemMapper.deleteOrderItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
