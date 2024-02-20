package com.springboot.service.customer.review;

import com.springboot.dto.OrderedProductsResponseDto;
import com.springboot.dto.ProductDto;
import com.springboot.dto.ReviewDto;
import com.springboot.entity.*;
import com.springboot.repository.OrderRepo;
import com.springboot.repository.ProductRepo;
import com.springboot.repository.ReviewRepo;
import com.springboot.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final ReviewRepo reviewRepo;

    public OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId){
        Optional<Order> optionalOrder = orderRepo.findById(orderId);

        OrderedProductsResponseDto orderedProductsResponseDto = new OrderedProductsResponseDto();
        if(optionalOrder.isPresent()){
            orderedProductsResponseDto.setOrderedAmount(optionalOrder.get().getAmount());

            List<ProductDto> productDtosList = new ArrayList<>();
            for(CartItems cartItems : optionalOrder.get().getCartItems()){
                ProductDto productDto = new ProductDto();

                productDto.setId(cartItems.getProduct().getId());
                productDto.setName(cartItems.getProduct().getName());
                productDto.setPrice(cartItems.getPrice());
                productDto.setQuantity(cartItems.getQuantity());

                productDto.setByteImg(cartItems.getProduct().getImg());

                productDtosList.add(productDto);
            }
            orderedProductsResponseDto.setProductDtoList(productDtosList);
        }
        return orderedProductsResponseDto;
    }

    public ReviewDto giveReview(ReviewDto reviewDto) throws IOException {
        Optional<Product> optionalProduct = productRepo.findById(reviewDto.getProductId());
        Optional<User> optionalUser = userRepo.findById(reviewDto.getUserId());

        if(optionalProduct.isPresent() && optionalUser.isPresent()){
            Review review = new Review();
            review.setRating(reviewDto.getRating());
            review.setDescription(reviewDto.getDescription());
            review.setProduct(optionalProduct.get());
            review.setUser(optionalUser.get());
            review.setImg(reviewDto.getImg().getBytes());

            return reviewRepo.save(review).getDto();
        }
        return null;
    }
}
