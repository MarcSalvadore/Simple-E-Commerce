package com.apapedia.order.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apapedia.order.model.Cart;
import java.util.Optional;


@Repository
public interface CartDb extends JpaRepository<Cart, UUID> {

    Optional<Cart> findByUserId(UUID userId);
    
}
