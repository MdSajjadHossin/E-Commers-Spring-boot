package com.springboot.service.customer;

import com.springboot.dto.OrderDto;
import com.springboot.dto.ProductDetailsDto;
import com.springboot.dto.ProductDto;
import com.springboot.entity.FAQ;
import com.springboot.entity.Product;
import com.springboot.entity.Review;
import com.springboot.repository.FAQRepo;
import com.springboot.repository.ProductRepo;
import com.springboot.repository.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final ProductRepo productRepo;

    private final FAQRepo faqRepo;

    private final ReviewRepo reviewRepo;

    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepo.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public List<ProductDto> getAllProductByName(String name){
        List<Product> products = productRepo.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public ProductDetailsDto getProductDetailsById(Long productId){
        Optional<Product> optionalProduct = productRepo.findById(productId);

        if(optionalProduct.isPresent()){
            List<FAQ> faqList = faqRepo.findAllByProductId(productId);
            List<Review> reviewList = reviewRepo.findAllByProductId(productId);

            ProductDetailsDto productDetailsDto = new ProductDetailsDto();

            productDetailsDto.setProductDto(optionalProduct.get().getDto());
            productDetailsDto.setFaqDtoList(faqList.stream().map(FAQ::getFAQDto).collect(Collectors.toList()));
            productDetailsDto.setReviewDtoList(reviewList.stream().map(Review::getDto).collect(Collectors.toList()));

            return productDetailsDto;

        }
        return null;
    }
}
