package com.springboot.service.customer;

import com.springboot.dto.OrderDto;
import com.springboot.dto.ProductDto;
import com.springboot.entity.Product;
import com.springboot.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final ProductRepo productRepo;

    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepo.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public List<ProductDto> getAllProductByName(String name){
        List<Product> products = productRepo.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

}
