package com.springboot.controller.customer;

import com.springboot.dto.OrderedProductsResponseDto;
import com.springboot.dto.ReviewDto;
import com.springboot.service.customer.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/orderedProducts/{orderId}")
    public ResponseEntity<OrderedProductsResponseDto> getOrderedProductsDetailsByOrderId(@PathVariable Long orderId){
        return ResponseEntity.ok(reviewService.getOrderedProductsDetailsByOrderId(orderId));
    }

    @PostMapping("/reviews")
    public ResponseEntity<?> review(@ModelAttribute ReviewDto reviewDto) throws IOException {
        ReviewDto reviews = reviewService.giveReview(reviewDto);
        if(reviews == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewDto);
    }
}
