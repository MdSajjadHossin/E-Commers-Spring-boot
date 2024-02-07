package com.springboot.controller.customer;

import com.springboot.dto.AddProductCartDto;
import com.springboot.dto.OrderDto;
import com.springboot.dto.PlaceOrderDto;
import com.springboot.exceptions.ValidationException;
import com.springboot.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductCartDto addProductCartDto){
        return cartService.addProductToCart(addProductCartDto);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<?> getProductToCart(@PathVariable Long userId){
        OrderDto orderDto = cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @GetMapping("/coupon/{userId}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long userId, @PathVariable String code){
        try{
            OrderDto orderDto = cartService.applyCoupons(userId, code);
            return ResponseEntity.ok(orderDto);
        } catch (ValidationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/addition")
    public ResponseEntity<?> increaseProductQuantity(@PathVariable AddProductCartDto addProductCartDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.increaseProductQuantity(addProductCartDto));
    }

    @PostMapping("/deduction")
    public ResponseEntity<?> decreaseProductQuantity(@PathVariable AddProductCartDto addProductCartDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.decreaseProductQuantity(addProductCartDto));
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@PathVariable PlaceOrderDto placeOrderDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.placeOrder(placeOrderDto));
    }

}
