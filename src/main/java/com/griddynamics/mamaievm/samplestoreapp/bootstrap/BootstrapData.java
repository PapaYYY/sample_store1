package com.griddynamics.mamaievm.samplestoreapp.bootstrap;

import com.griddynamics.mamaievm.samplestoreapp.entity.Product;
import com.griddynamics.mamaievm.samplestoreapp.entity.ProductInventory;
import com.griddynamics.mamaievm.samplestoreapp.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BootstrapData implements CommandLineRunner {
    
    private final ProductRepository productRepository;
    
    @Override
    public void run(String... args) {
        log.info("Loading product data...");
        Product p1 = Product.builder()
                .price(BigDecimal.valueOf(1L))
                .title("Product 1")
                .productInventory(ProductInventory
                        .builder()
                        .available(10)
                        .build())
                .build();
        productRepository.save(p1);
        Product p2 = Product.builder()
                .price(BigDecimal.valueOf(2L))
                .title("Product 2")
                .productInventory(ProductInventory
                        .builder()
                        .available(20)
                        .build())
                .build();
        productRepository.save(p2);
        log.info("Product data loaded: " + productRepository.count() + " products");
    }

}
