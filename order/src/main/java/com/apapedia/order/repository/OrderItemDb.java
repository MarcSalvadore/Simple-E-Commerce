package com.apapedia.order.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apapedia.order.model.OrderItem;

@Repository
public interface OrderItemDb extends JpaRepository<OrderItem, UUID> {

    
} 
