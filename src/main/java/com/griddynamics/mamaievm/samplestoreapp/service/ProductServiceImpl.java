package com.griddynamics.mamaievm.samplestoreapp.service;

import com.griddynamics.mamaievm.samplestoreapp.dto.ProductDto;
import com.griddynamics.mamaievm.samplestoreapp.entity.Product;
import com.griddynamics.mamaievm.samplestoreapp.mapper.ProductMapper;
import com.griddynamics.mamaievm.samplestoreapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        if(productDto.getId() == null) {
            Product product = productMapper.toEntity(productDto);
            return productMapper.toDto(productRepository.save(product));
        } else {
            Product product = productRepository.findById(productDto.getId()).orElseThrow();
            Product updatedProduct = productMapper.partialUpdate(productDto, product);
            return productMapper.toDto(productRepository.save(updatedProduct));
        }
    }

    @Override
    public List<ProductDto> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto findProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        return productMapper.toDto(product);
    }

}
