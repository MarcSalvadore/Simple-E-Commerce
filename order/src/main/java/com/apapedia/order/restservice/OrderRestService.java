package com.apapedia.order.restservice;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.model.Order;
import com.apapedia.order.model.OrderItem;

public interface OrderRestService {
    void createRestCart(Cart cart);

    void createRestCartItem(CartItem cartItem);

    void createRestOrder(Order order);

    void createRestOrderItem(OrderItem orderItem);

    Cart getCartById(UUID idCart);

    List<Order> getRestOrderByCustomerId(UUID customerId);
    List<Order> getRestOrderBySellerId(UUID sellerId);
    void updateOrderStatus(UUID orderId, int newStatus);
}
