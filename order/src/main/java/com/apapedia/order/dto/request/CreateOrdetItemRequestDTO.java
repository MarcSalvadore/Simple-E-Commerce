package com.apapedia.order.dto.request;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateOrdetItemRequestDTO {

    @JsonAlias("product_id")
    private UUID productId;

    private int quantity;

    @JsonAlias("product_name")
    private String productName;

    @JsonAlias("product_price")
    private int productPrice;
    
}
