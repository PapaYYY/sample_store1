package com.griddynamics.mamaievm.samplestoreapp.repository;

import com.griddynamics.mamaievm.samplestoreapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}