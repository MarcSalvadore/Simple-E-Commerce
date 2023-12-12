package com.apapedia.order.restservice;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

    void editCartItemQuantity(CartItem cartItem);

    List<CartItem> retrieveRestAllCartItem();

    CartItem getRestCartItemById(UUID cartItemId);

    void deleteCartItem(UUID cartItem);
    Cart getCartById(UUID idCart);

    List<Order> getRestOrderByCustomerId(UUID customerId);
    List<Order> getRestOrderBySellerId(UUID sellerId);
    void updateOrderStatus(UUID orderId, int newStatus);
    Map<String, Long> chartTop5SoldProduct(UUID sellerId);
    List<Order> getMonthlyOrderBySeller(UUID sellerId, LocalDate startDate, LocalDate endDate);
    List<Order> getCompletedOrdersBySellerId(UUID sellerId);
}
