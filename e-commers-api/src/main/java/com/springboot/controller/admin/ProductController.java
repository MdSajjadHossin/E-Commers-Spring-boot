package com.springboot.controller.admin;

import com.springboot.dto.ProductDto;
import com.springboot.service.admin.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/admin/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto dtoProduct = productService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoProduct);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProduct(){
        List<ProductDto> productsDto = productService.getAllProducts();
        return ResponseEntity.ok(productsDto);
    }

    @GetMapping("/products/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name){
        List<ProductDto> productDto = productService.getAllProductByName(name);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        boolean deleted = productService.deleteProduct(productId);
        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
