package com.griddynamics.mamaievm.samplestoreapp.repository;

import com.griddynamics.mamaievm.samplestoreapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}