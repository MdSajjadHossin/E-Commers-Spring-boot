package com.springboot.service.customer.cart;

import com.springboot.dto.AddProductCartDto;
import com.springboot.dto.OrderDto;
import com.springboot.dto.PlaceOrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CartService {

    ResponseEntity<?> addProductToCart(AddProductCartDto addProductCartDto);
    OrderDto getCartByUserId(Long userId);
    OrderDto applyCoupons(Long userId, String code);
    OrderDto increaseProductQuantity(AddProductCartDto addProductCartDto);
    OrderDto decreaseProductQuantity(AddProductCartDto addProductCartDto);
    OrderDto placeOrder(PlaceOrderDto placeOrderDto);
    public List<OrderDto> getAllPlacedOrder(Long userId);
    OrderDto searchOrderByTrackingId(UUID trackingId);
}
