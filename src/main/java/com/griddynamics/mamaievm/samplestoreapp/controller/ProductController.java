package com.griddynamics.mamaievm.samplestoreapp.controller;

import com.griddynamics.mamaievm.samplestoreapp.dto.ProductDto;
import com.griddynamics.mamaievm.samplestoreapp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ProductController.BASE_URL)
@RequiredArgsConstructor
public class ProductController {
    
    public static final String BASE_URL = "/api/v1/products";
    
    private final ProductService productService;

    @GetMapping()
    List<ProductDto> getAllProducts(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    ProductDto getProductById(@PathVariable Long id){
        return productService.findById(id);
    }
    
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    ProductDto createProduct(@RequestBody @NonNull @Valid ProductDto product) {
        if(product.getId() != null) {
            throw new IllegalArgumentException("Please remove product id from create product request body");
        }
        return productService.save(product);
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    ProductDto updateProduct(@RequestBody @NonNull @Valid ProductDto product) {
        if(product.getId() == null) {
            throw new IllegalArgumentException("Product id is required for product update");
        }
        return productService.save(product);
    }
}
