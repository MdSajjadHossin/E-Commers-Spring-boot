package com.springboot.service.customer.cart;

import com.springboot.dto.AddProductCartDto;
import com.springboot.dto.CartItemsDto;
import com.springboot.dto.OrderDto;
import com.springboot.dto.PlaceOrderDto;
import com.springboot.entity.*;
import com.springboot.enums.OrderStatus;
import com.springboot.exceptions.ValidationException;
import com.springboot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CartServiceImpl implements CartService{

    private final OrderRepo orderRepo;


    private final UserRepo userRepo;

    private final CartItemsRepo cartItemsRepo;

    private final ProductRepo productRepo;

    private final CouponRepo couponRepo;

    public ResponseEntity<?> addProductToCart(AddProductCartDto addProductCartDto){
        Order activeOrder = orderRepo.findByUserIdAndOrderStatus(addProductCartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItems> optionalCartItems = cartItemsRepo.findByProductIdAndOrderIdAndUserId(addProductCartDto.getProductId(), activeOrder.getId(), addProductCartDto.getUserId());

        if(optionalCartItems.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }else{
            Optional<Product> optionalProduct = productRepo.findById(addProductCartDto.getProductId());
            Optional<User> optionalUser = userRepo.findById(addProductCartDto.getUserId());

            if(optionalProduct.isPresent() && optionalUser.isPresent()){
                CartItems cart = new CartItems();
                cart.setProduct(optionalProduct.get());
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);

                CartItems updateCart = cartItemsRepo.save(cart);

                activeOrder.setTotalAmount(activeOrder.getAmount() + cart.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + cart.getPrice());
                activeOrder.getCartItems().add(cart);
                orderRepo.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cart);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product or User not found");
            }
        }
    }

    public OrderDto getCartByUserId(Long userId){
        Order activeOrder = orderRepo.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CartItemsDto> cartItemsDtoList = activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());

        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtoList);

        if(activeOrder.getCoupon() != null){
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }

        return orderDto;
    }

    public OrderDto applyCoupons(Long userId, String code){
        Order activeOrder = orderRepo.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);

        Coupon coupon = couponRepo.findByCode(code).orElseThrow(()-> new ValidationException("Coupon not found"));

        if(couponIsExpired(coupon)){
            throw new ValidationException("Coupon is Expired");
        }
        double discountAmount = ((coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount() - discountAmount;

        activeOrder.setAmount((long)netAmount);
        activeOrder.setDiscount((long)discountAmount);

        activeOrder.setCoupon(coupon);

        orderRepo.save(activeOrder);
        return activeOrder.getOrderDto();
    }

    private boolean couponIsExpired(Coupon coupon) {
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpiredDate();

        return expirationDate != null && currentDate.after(expirationDate);
    }

    public OrderDto increaseProductQuantity(AddProductCartDto addProductCartDto){
        Order activeOrder = orderRepo.findByUserIdAndOrderStatus(addProductCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepo.findById(addProductCartDto.getProductId());

        Optional<CartItems> optionalCartItems = cartItemsRepo.findByProductIdAndOrderIdAndUserId(addProductCartDto.getProductId(), activeOrder.getId(), addProductCartDto.getUserId());

        if(optionalProduct.isPresent() && optionalCartItems.isPresent()){
            CartItems cartItems = optionalCartItems.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());

            cartItems.setQuantity(cartItems.getQuantity() + 1);
            if(activeOrder.getCoupon() != null){
                double discountAmount = ((activeOrder.getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount((long)netAmount);
                activeOrder.setDiscount((long)discountAmount);
            }
            cartItemsRepo.save(cartItems);
            orderRepo.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

    public OrderDto decreaseProductQuantity(AddProductCartDto addProductCartDto){
        Order activeOrder = orderRepo.findByUserIdAndOrderStatus(addProductCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepo.findById(addProductCartDto.getProductId());

        Optional<CartItems> optionalCartItems = cartItemsRepo.findByProductIdAndOrderIdAndUserId(addProductCartDto.getProductId(), activeOrder.getId(), addProductCartDto.getUserId());

        if(optionalProduct.isPresent() && optionalCartItems.isPresent()){
            CartItems cartItems = optionalCartItems.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());

            cartItems.setQuantity(cartItems.getQuantity() - 1);
            if(activeOrder.getCoupon() != null){
                double discountAmount = ((activeOrder.getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount((long)netAmount);
                activeOrder.setDiscount((long)discountAmount);
            }
            cartItemsRepo.save(cartItems);
            orderRepo.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto){
        Order activeOrder = orderRepo.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
        Optional<User> optionalUser = userRepo.findById(placeOrderDto.getUserId());
        if(optionalUser.isPresent()){
            activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            activeOrder.setAddress(placeOrderDto.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.Placed);
            activeOrder.setTrackingId(UUID.randomUUID());

            orderRepo.save(activeOrder);

            Order order = new Order();
            order.setAmount(0L);
            order.setTotalAmount(0L);
            order.setDiscount(0L);
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.Pending);
            orderRepo.save(order);

            return activeOrder.getOrderDto();

        }
        return null;
    }

    public List<OrderDto> getAllPlacedOrder(Long userId){
        return orderRepo.findByUserIdAndOrderStatusIn(userId, List.of(OrderStatus.Shipped, OrderStatus.Placed, OrderStatus.Delivered)).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

}
