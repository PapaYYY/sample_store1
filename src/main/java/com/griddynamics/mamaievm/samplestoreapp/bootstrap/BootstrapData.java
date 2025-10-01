package com.griddynamics.mamaievm.samplestoreapp.bootstrap;

import com.griddynamics.mamaievm.samplestoreapp.entity.Product;
import com.griddynamics.mamaievm.samplestoreapp.entity.ProductInventory;
import com.griddynamics.mamaievm.samplestoreapp.mapper.ProductMapper;
import com.griddynamics.mamaievm.samplestoreapp.repository.ProductRepository;
import com.griddynamics.mamaievm.samplestoreapp.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("test-data")
public class BootstrapData implements CommandLineRunner {

    private final ProductMapper productMapper;
    private final ProductService productService;

    @Override
    public void run(String... args) {
        log.info("Loading product data...");
        Product p1 = Product.builder()
                .price(BigDecimal.valueOf(1L))
                .title("Product 1")
                .build();
        p1.setProductInventory(ProductInventory
                .builder()
                .available(10)
                .build());
        productService.save(productMapper.toDto(p1));
        Product p2 = Product.builder()
                .price(BigDecimal.valueOf(2L))
                .title("Product 2")
                .build();
        p2.setProductInventory(ProductInventory
                .builder()
                .available(20)
                .build());
        productService.save(productMapper.toDto(p2));
        log.info("Product data loaded: " + productService.findAll().size() + " products");
    }

}
