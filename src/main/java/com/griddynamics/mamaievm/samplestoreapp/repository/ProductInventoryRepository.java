package com.griddynamics.mamaievm.samplestoreapp.repository;

import com.griddynamics.mamaievm.samplestoreapp.entity.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {
}