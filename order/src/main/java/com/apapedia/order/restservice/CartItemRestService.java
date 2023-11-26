package com.apapedia.order.restservice;

import com.apapedia.order.model.CartItem;

import java.util.*;

public interface CartItemRestService {
    CartItem createRestCartItem(CartItem cartItem);
    List<CartItem> getCartItemByUserId(UUID userId);
}
