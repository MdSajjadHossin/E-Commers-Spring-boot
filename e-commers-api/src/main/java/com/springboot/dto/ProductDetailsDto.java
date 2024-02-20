package com.springboot.dto;

import com.springboot.entity.FAQ;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailsDto {

    private ProductDto productDto;

    private List<ReviewDto> reviewDtoList;

    private List<FAQDto> faqDtoList;
}
