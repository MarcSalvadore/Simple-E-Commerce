package com.apapedia.order.dto.request;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateOrderRequestDTO {

    @JsonAlias("seller_id")
    private UUID seller;
    @JsonAlias("customer_id")
    private UUID customer;
    @JsonAlias("order_items")
    private List<CreateOrdetItemRequestDTO> orderItems;
    
}
