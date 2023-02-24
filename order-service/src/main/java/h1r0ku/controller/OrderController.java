package h1r0ku.controller;

import h1r0ku.dto.request.OrderItemRequest;
import h1r0ku.dto.request.OrderRequest;
import h1r0ku.dto.response.OrderItemResponse;
import h1r0ku.dto.response.OrderResponse;
import h1r0ku.mapper.OrderItemMapper;
import h1r0ku.mapper.OrderMapper;
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
public class OrderController {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderMapper.create(orderRequest));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(orderMapper.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderMapper.getOrderById(id));
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<Page<OrderItemResponse>> getOrderItemsByOrder(@PathVariable("id") Long id,
                                                                        @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(orderMapper.getOrderItemsByOrder(id, pageable));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<OrderResponse>> getOrdersByUser(@PathVariable("id") Long id,
                                                               @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(orderMapper.getOrdersByUser(id, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable("id") Long id,
                                                     @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderMapper.updateOrder(id, orderRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//  Order items

    @PostMapping("/items")
    public ResponseEntity<OrderItemResponse> createOrderItem(@RequestBody OrderItemRequest orderItemRequest) {
        return ResponseEntity.ok(orderItemMapper.create(orderItemRequest));
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<OrderItemResponse> updateOrderItem(@PathVariable("id") Long id,
                                                             @RequestBody OrderItemRequest orderItemRequest) {
        return ResponseEntity.ok(orderItemMapper.updateOrderItem(id, orderItemRequest));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
