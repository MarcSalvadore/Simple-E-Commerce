package com.apapedia.order.restservice;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.model.Order;
import com.apapedia.order.model.OrderItem;

public interface OrderRestService {
    void createRestCart(Cart cart);

    void createRestCartItem(CartItem cartItem);

    void createRestOrder(Order order);

    void createRestOrderItem(OrderItem orderItem);
}
