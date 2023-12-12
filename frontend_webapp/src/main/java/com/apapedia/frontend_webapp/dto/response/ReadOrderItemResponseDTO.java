package com.apapedia.frontend_webapp.dto.response;

import org.springframework.context.annotation.Configuration;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Configuration
public class ReadOrderItemResponseDTO {
    private String id ;

    // @JsonAlias("product_id")
    private String productId;

	// @JsonAlias("order_id")
	// private ReadOrderResponseDTO orderId;

    private Integer quantity;

    // @JsonAlias("product_name")
    private String productName;

    // @JsonAlias("product_price")
    private Integer productPrice;
    
}
