package com.apapedia.order.restcontroller;

import java.util.*;

import javax.xml.catalog.Catalog;

import com.apapedia.order.restservice.CartItemRestService;
import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class CartRestController {

    @Autowired
    private CartItemRestService cartItemRestService;
    
    @GetMapping(value = "/cart/customer/{userId}")
    public List<CartItem> getCartItemByUserId(@PathVariable("userId") UUID userId) {
        try {
            return cartItemRestService.getCartItemByUserId(userId);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cart item with user " + userId + " not found"
            );
        }
    }
}
