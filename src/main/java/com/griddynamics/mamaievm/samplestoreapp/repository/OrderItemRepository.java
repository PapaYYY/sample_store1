package com.griddynamics.mamaievm.samplestoreapp.repository;

import com.griddynamics.mamaievm.samplestoreapp.entity.OrderItem;
import com.griddynamics.mamaievm.samplestoreapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    OrderItem findFirstByProduct(Product product);
}