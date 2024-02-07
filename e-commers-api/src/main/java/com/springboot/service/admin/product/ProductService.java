package com.springboot.service.admin.product;

import com.springboot.dto.ProductDto;
import com.springboot.entity.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto) throws IOException;
    List<ProductDto> getAllProducts();
    List<ProductDto> getAllProductByName(String name);
    boolean deleteProduct(Long id);
}
