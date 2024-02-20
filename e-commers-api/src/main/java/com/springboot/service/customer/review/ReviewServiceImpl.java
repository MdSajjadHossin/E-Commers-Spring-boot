package com.springboot.service.customer.review;

import com.springboot.dto.OrderedProductsResponseDto;
import com.springboot.dto.ProductDto;
import com.springboot.entity.CartItems;
import com.springboot.entity.Order;
import com.springboot.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final OrderRepo orderRepo;

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
}
