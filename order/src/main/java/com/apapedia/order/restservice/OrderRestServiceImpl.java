package com.apapedia.order.restservice;

import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.apapedia.order.dto.response.TopOrderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
    public Cart getCartById(UUID idCart) {
        return cartDb.findById(idCart).orElseThrow();
    }

    @Override
    public List<Order> getRestOrderByCustomerId(UUID customerId) {
        return orderDb.findByCustomer(customerId);
    }

    @Override
    public List<Order> getRestOrderBySellerId(UUID sellerId) {
        return orderDb.findBySeller(sellerId);
    }

    @Override
    public void updateOrderStatus(UUID orderId, int newStatus) {
        orderDb.updateOrderStatus(orderId, newStatus);
     }

    @Override
    public Map<String, Long> chartTop5SoldProduct(UUID sellerId){
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().withDayOfMonth(startDate.lengthOfMonth());

        // Filter orders for the given seller and within the month interval
        List<Order> sellerOrders = getMonthlyOrderBySeller(sellerId, startDate, endDate);

        // Flatten the list of order items
        List<OrderItem> allOrderItems = sellerOrders.stream()
                .flatMap(order -> order.getListOrderItem().stream())
                .collect(Collectors.toList());

        // Group and sum the quantities sold by product name
        Map<String, Long> productSoldMap = allOrderItems.stream()
                .collect(Collectors.groupingBy(OrderItem::getProductName,
                        Collectors.summingLong(OrderItem::getQuantity)));

        // Sort the result by quantity sold in descending order
        Map<String, Long> sortedProductSoldMap = productSoldMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        // Take the top 5 items
        return sortedProductSoldMap.entrySet().stream()
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public List<Order> getMonthlyOrderBySeller(UUID sellerId, LocalDate startDate, LocalDate endDate) {
        return orderDb.findBySeller(sellerId);
    }

    @Override
    public List<Order> getCompletedOrdersBySellerId(UUID sellerId) {
        return orderDb.findBySellerAndStatus(sellerId, 5);
    }
}
