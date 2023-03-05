package h1r0ku.controller;

import h1r0ku.dto.cart.CartRequest;
import h1r0ku.dto.request.CartItemRequest;
import h1r0ku.dto.cart.CartResponse;
import h1r0ku.mapper.CartMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
@Tag(name = "Cart Controller")
public class CartController {

    private final CartMapper cartMapper;

    @PostMapping
    @Operation(summary = "Create a new cart", description = "Creates a new cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created cart"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<CartResponse> createProduct(@Valid @RequestBody CartRequest cartRequest) {
        return ResponseEntity.ok(cartMapper.create(cartRequest));
    }

    @GetMapping("/{cartId}")
    @Operation(summary = "Get a cart by ID", description = "Returns a single cart identified by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cart"),
            @ApiResponse(responseCode = "404", description = "cart not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CartResponse> getCartById(@PathVariable("cartId") Long cartId) {
        return ResponseEntity.ok(cartMapper.getById(cartId));
    }

    @PutMapping("/{cartId}/complete")
    @Operation(summary = "Create order", description = "Clear customer cart and create order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created order"),
            @ApiResponse(responseCode = "404", description = "Cart not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CartResponse> completeCart(@PathVariable("cartId") Long cartId) {
        return ResponseEntity.ok(cartMapper.completeCart(cartId));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get a cart by customer id", description = "Get a cart by customer id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cart"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CartResponse> getCartByCustomerId(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(cartMapper.getByCustomerId(customerId));
    }

//    Cart items

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Create a new cart item", description = "Returns a single cart identified by customer id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created cart item"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<CartResponse> createCartItem(@PathVariable("cartId") Long cartId, @Valid @RequestBody CartItemRequest cartItemRequest) {
        return ResponseEntity.ok(cartMapper.addItem(cartId, cartItemRequest));
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    @Operation(summary = "Delete cart item", description = "Delete cart item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted cart item"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<CartResponse> deleteCartItem(@PathVariable("cartId") Long cartId, @PathVariable("itemId") Long itemId) {
        return ResponseEntity.ok(cartMapper.removeItem(cartId, itemId));
    }
}
