package com.griddynamics.mamaievm.samplestoreapp.service;

import com.griddynamics.mamaievm.samplestoreapp.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto saveProduct(ProductDto productDto);

    List<ProductDto> findAllProducts();
    
    ProductDto findProductById(Long productId);
}
