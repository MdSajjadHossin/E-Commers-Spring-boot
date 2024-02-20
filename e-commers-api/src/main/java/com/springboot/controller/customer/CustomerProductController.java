package com.springboot.controller.customer;

import com.springboot.dto.ProductDetailsDto;
import com.springboot.dto.ProductDto;
import com.springboot.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerProductController {

    private final CustomerService customerService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProduct(){
        List<ProductDto> productsDto = customerService.getAllProducts();
        return ResponseEntity.ok(productsDto);
    }

    @GetMapping("/products/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name){
        List<ProductDto> productDto = customerService.getAllProductByName(name);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDetailsDto> getProductsDetailById(@PathVariable Long productId){
        ProductDetailsDto productDetailsDto = customerService.getProductDetailsById(productId);
        if(productDetailsDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDetailsDto);
    }
}
