package com.apapedia.order.restcontroller;

import java.util.*;
import com.apapedia.order.dto.request.*;
import com.apapedia.order.dto.response.SellerResponseDTO;
import com.apapedia.order.repository.OrderDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.order.dto.CartMapper;
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

    @Autowired
    OrderDb orderDb;

    @Autowired
    CartMapper cartMapper;

    @PostMapping(value = "/cart/create")
    public Cart restAddCart(@Valid @RequestBody CreateCartRequestDTO cartDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            var cart = cartMapper.createCartRequestDTOToCart(cartDTO);
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

    @PutMapping(value = "/cartitem/editQuantity")
    public CartItem restEditCartItemQuantity(@Valid @RequestBody CartItem cartItem, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            orderRestService.editCartItemQuantity(cartItem);
            return cartItem;
        }
    }

    @DeleteMapping(value = "/cartitem/delete/{cartItemId}")
    public void restDeleteCartItem(@PathVariable("cartItemId") String cartItemId) {
        orderRestService.deleteCartItem(UUID.fromString(cartItemId));
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
    
    @PostMapping(value = "/cart_item")
    public CartItem restAddCartItem(@Valid @RequestBody CreateCartItemRequestDTO cartItemRequest, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            // CartItem cartItem = cartItemMapper.createCartItemRequestDTOToCartItem(cartItemRequest);
            Cart cart = orderRestService.getCartById(cartItemRequest.getCartId());

            CartItem cartItem = new CartItem();
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setQuantity(cartItemRequest.getQuantity());
            
            cartItem.setCartId(cart);

            orderRestService.createRestCartItem(cartItem);
            return cartItem;
        }
    }

    @PostMapping(value = "/order/add")
    public String restAddOrder(@Valid @RequestBody CreateOrderRequestDTO orderRequest, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {

            int totalPrice = 0;

            List<OrderItem> orderItems = new ArrayList<>();

            Order order = new Order();
            order.setId(UUID.randomUUID());
            order.setCustomer(orderRequest.getCustomer());
            order.setSeller(orderRequest.getSeller());

            for(CreateOrdetItemRequestDTO orderItemReq : orderRequest.getOrderItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(order);
                orderItem.setProductId(orderItemReq.getProductId());
                orderItem.setProductName(orderItemReq.getProductName());
                orderItem.setProductPrice(orderItemReq.getProductPrice());
                orderItem.setQuantity(orderItemReq.getQuantity());

                orderItems.add(orderItem);

                totalPrice += orderItemReq.getProductPrice();
            }

            order.setTotalPrice(totalPrice);
            order.setListOrderItem(orderItems);

            orderRestService.createRestOrder(order);

            return "Success";
        }
    }

    @GetMapping("/order/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable("customerId") UUID customerId) {
        List<Order> orders = orderRestService.getRestOrderByCustomerId(customerId);

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok(orders); 
        }
    }

    @GetMapping("/order/seller/{sellerId}")
    public ResponseEntity<List<Order>> getOrdersBySellerId(@PathVariable("sellerId") UUID sellerId) {
        List<Order> orders = orderRestService.getRestOrderBySellerId(sellerId);

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok(orders);
        }
    }

    @PutMapping(value = "/order/status/{orderId}")
    public String updateOrderStatus(@PathVariable("orderId") UUID orderId, @Valid @RequestBody UpdateOrderStatusRequestDTO orderStatusReq, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            orderRestService.updateOrderStatus(orderId, orderStatusReq.getStatus());
            return "success";
        }
    }

    @GetMapping(value = "/order/chart/{sellerId}")
    public ResponseEntity<Map<String, Long>> chartTopFiveOrder(@PathVariable("sellerId") UUID sellerId, Model model) {
        // Fetch the top 5 sold products for the seller within the month interval
        Map<String, Long> listTopOrder = orderRestService.chartTop5SoldProduct(sellerId);

        // Returning the response for the chart data
        return new ResponseEntity<>(listTopOrder, HttpStatus.OK);
    }

    // Update seller balance
    @PostMapping(value = "/order/topup")
    public ResponseEntity<String> restTopUpOrder(@RequestBody ReadTopUpRequestDTO topUpDTO) {
        try {
            // Assuming you have a method to retrieve completed orders with status 5
            List<Order> completedOrders = orderRestService.getCompletedOrdersBySellerId(topUpDTO.getSellerId());

            // Iterate through completed orders and top up the seller's balance
            for (Order order : completedOrders) {
                // Assuming you have a SellerResponseDTO to send to the Seller service
                SellerResponseDTO sellerResponseDTO = new SellerResponseDTO();
                sellerResponseDTO.setSellerId(order.getSeller());
                sellerResponseDTO.setAmount(Long.valueOf(order.getTotalPrice()));

                // Call the Seller service to update the seller's balance
                String sellerUri = "http://localhost:8081/api/topup";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.postForEntity(sellerUri, sellerResponseDTO, String.class);
            }

            return ResponseEntity.ok("Top-up for completed orders successful.");
        } catch (Exception e) {
            // Handle exceptions, log errors, and return an appropriate response
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Failed to top-up seller's balance", e
            );
        }
    }
}
