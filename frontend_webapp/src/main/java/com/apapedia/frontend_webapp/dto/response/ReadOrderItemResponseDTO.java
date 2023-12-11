package com.apapedia.frontend_webapp.dto.response;

import org.hibernate.validator.constraints.UUID;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Configuration
public class ReadOrderItemResponseDTO {
    private UUID id ;

    @JsonAlias("product_id")
    private UUID productId;

	@JsonAlias("order_id")
	private ReadOrderResponseDTO orderId;

    private Integer quantity;

    @JsonAlias("product_name")
    private String productName;

    @JsonAlias("product_price")
    private Integer productPrice;
    
}
