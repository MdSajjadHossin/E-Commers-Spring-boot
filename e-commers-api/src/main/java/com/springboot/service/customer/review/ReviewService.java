package com.springboot.service.customer.review;

import com.springboot.dto.OrderedProductsResponseDto;

public interface ReviewService {
    public OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);
}
