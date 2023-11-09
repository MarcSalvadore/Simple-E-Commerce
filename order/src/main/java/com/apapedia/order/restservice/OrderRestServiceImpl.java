package com.apapedia.order.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.model.Order;
import com.apapedia.order.model.OrderItem;
import com.apapedia.order.repository.CartDb;
import com.apapedia.order.repository.CartItemDb;
import com.apapedia.order.repository.OrderDb;
import com.apapedia.order.repository.OrderItemDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderRestServiceImpl implements OrderRestService {
    @Autowired
    CartDb cartDb;

    @Autowired
    CartItemDb cartItemDb;

    @Autowired
    OrderDb orderDb;

    @Autowired
    OrderItemDb orderItemDb;

    @Override
    public void createRestCart(Cart cart) { cartDb.save(cart); }

    @Override
    public void createRestCartItem(CartItem cartItem) { cartItemDb.save(cartItem); }

    @Override
    public void createRestOrder(Order order) { orderDb.save(order); };

    @Override
    public void createRestOrderItem(OrderItem orderItem) { orderItemDb.save(orderItem); };
}
