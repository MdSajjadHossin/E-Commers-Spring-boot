package com.springboot.controller.admin;

import com.springboot.dto.FAQDto;
import com.springboot.dto.ProductDto;
import com.springboot.service.admin.faq.FAQService;
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

    private final FAQService faqService;

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

    @PostMapping("/faq/{productId}")
    public ResponseEntity<FAQDto> postFAQ(@PathVariable Long productId, @PathVariable FAQDto faqDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(faqService.postFAQ(productId, faqDto));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getAllProductById(@PathVariable Long id){
        ProductDto productDto = productService.getProductById(id);
        if(productDto != null){
            return ResponseEntity.ok(productDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @ModelAttribute ProductDto productDto) throws IOException {
        ProductDto updateProduct = productService.updateProduct(productId, productDto);
        if(productDto != null){
            return ResponseEntity.ok(productDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
