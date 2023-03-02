package h1r0ku.controller;

import h1r0ku.dto.request.ReviewRequest;
import h1r0ku.dto.response.ReviewResponse;
import h1r0ku.mapper.ReviewMapper;
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
@RequestMapping("/api/v1/catalog/reviews")
@Tag(name = "Review Controller")
public class ReviewController {

    private final ReviewMapper reviewMapper;

    @PostMapping
    @Operation(summary = "Create a new review", description = "Create a new review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<ReviewResponse> create(@Valid @RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.ok(reviewMapper.create(reviewRequest));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a review by ID", description = "Returns a single review identified by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the review"),
            @ApiResponse(responseCode = "404", description = "Review not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<ReviewResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reviewMapper.getById(id));
    }

    @GetMapping("/product/{id}")
    @Operation(summary = "Get Reviews by Product ID", description = "Retrieve all reviews belonging to a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the reviews"),
            @ApiResponse(responseCode = "404", description = "Review not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<Page<ReviewResponse>> getByProductId(@PathVariable("id") Long id, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(reviewMapper.getByProductId(id, pageable));
    }

    @GetMapping("/customer/{id}")
    @Operation(summary = "Get Reviews by Customer ID", description = "Retrieve all reviews belonging to a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the reviews"),
            @ApiResponse(responseCode = "404", description = "Review not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<Page<ReviewResponse>> getByCustomerId(@PathVariable("id") Long id, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(reviewMapper.getByCustomerId(id, pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Review", description = "Update a review by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Review not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable("id") Long id, @Valid @RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.ok(reviewMapper.update(id, reviewRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review by id", description = "Delete a review by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Review deleted"),
            @ApiResponse(responseCode = "404", description = "Review not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    public ResponseEntity<Void> deleteReview(@PathVariable("id") Long id) {
        reviewMapper.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
