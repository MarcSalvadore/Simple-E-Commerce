package com.apapedia.order.dto.request;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCartItemRequestDTO {

    @JsonProperty("cartId")
    private UUID cartUUID;

    private UUID productId;

    private Integer quantity;
}
