package com.apapedia.order.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.model.Order;
import com.apapedia.order.model.OrderItem;
import com.apapedia.order.restservice.OrderRestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class OrderRestController {
    @Autowired
    OrderRestService orderRestService;

    @PostMapping(value = "/cart/create")
    public Cart restAddCart(@Valid @RequestBody Cart cart, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            orderRestService.createRestCart(cart);
            return cart;
        }
    }

    @PostMapping(value = "/cartitem/add")
    public CartItem restAddCartItem(@Valid @RequestBody CartItem cartItem, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            orderRestService.createRestCartItem(cartItem);
            return cartItem;
        }
    }

    @PostMapping(value = "/order/create")
    public Order restAddOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            orderRestService.createRestOrder(order);
            return order;
        }
    }

    @PostMapping(value = "/orderitem/add")
    public OrderItem restAddOrderItem(@Valid @RequestBody OrderItem orderItem, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            orderRestService.createRestOrderItem(orderItem);
            return orderItem;
        }
    }
}
