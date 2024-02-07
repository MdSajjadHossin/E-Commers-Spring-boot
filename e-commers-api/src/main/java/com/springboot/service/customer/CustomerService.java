package com.springboot.service.customer;

import com.springboot.dto.ProductDto;

import java.util.List;

public interface CustomerService {

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductByName(String name);
}
