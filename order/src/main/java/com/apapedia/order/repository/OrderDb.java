package com.apapedia.order.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.apapedia.order.model.Order;

import jakarta.transaction.Transactional;

@Repository
public interface OrderDb extends JpaRepository<Order, UUID> {
    List<Order> findByCustomer(UUID customer);
    List<Order> findBySeller(UUID seller);
    List<Order> findBySellerAndStatus(UUID seller, Integer orderStatus);
    
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.status = :newStatus WHERE o.id = :orderId")
    void updateOrderStatus(UUID orderId, int newStatus);
}
