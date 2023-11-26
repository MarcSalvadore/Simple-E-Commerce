package com.apapedia.order.restservice;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.repository.CartItemDb;
import com.apapedia.order.repository.CartDb;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class CartItemRestServiceImpl implements CartItemRestService{
    
    @Autowired
    private CartItemDb cartItemDb;

    @Autowired
    private CartDb cartDb;

    
    @Override
    public CartItem createRestCartItem(CartItem cartItem) {
        return cartItemDb.save(cartItem);
    }


    @Override
    public List<CartItem> getCartItemByUserId(UUID userId) {
        Cart cart = cartDb.findByUserId(userId).orElseThrow();
        return cart.getListCartItem();
    }
}
