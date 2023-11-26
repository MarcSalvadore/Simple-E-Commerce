package com.apapedia.order.restservice;

import java.util.UUID;
import java.util.List;

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

    @Override
    public void editCartItemQuantity(CartItem updatedCartItem) {
        CartItem existingCartItem = cartItemDb.findById(updatedCartItem.getId())
                .orElseThrow(() -> new RuntimeException("Cart item not found with id: " + updatedCartItem.getId()));
        existingCartItem.setQuantity(updatedCartItem.getQuantity());
        cartItemDb.save(existingCartItem);
    }

    @Override
    public List<CartItem> retrieveRestAllCartItem() { return cartItemDb.findAll(); }

    @Override
    public CartItem getRestCartItemById(UUID id){
        for (CartItem cartItem : retrieveRestAllCartItem()) {
            if (cartItem.getId().equals(id)) {
                return cartItem;
            }
        }
        return null;
    };

    @Override
    public void deleteCartItem(UUID cartItemId) { 
        CartItem existingCartItemId = cartItemDb.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart item not found with id: " + cartItemId));
        cartItemDb.delete(existingCartItemId);
    }
}
