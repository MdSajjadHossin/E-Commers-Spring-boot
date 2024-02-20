package com.springboot.service.customer.review;

import com.springboot.dto.OrderedProductsResponseDto;
import com.springboot.dto.ReviewDto;

import java.io.IOException;

public interface ReviewService {
    public OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);

    public ReviewDto giveReview(ReviewDto reviewDto) throws IOException;
}
