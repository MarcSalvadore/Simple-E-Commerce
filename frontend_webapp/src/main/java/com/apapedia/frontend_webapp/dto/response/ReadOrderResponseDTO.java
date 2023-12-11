package com.apapedia.frontend_webapp.dto.response;

import org.hibernate.validator.constraints.UUID;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Configuration
public class ReadOrderResponseDTO {
    private UUID id;

    private Integer status;

    @JsonAlias("total_price")
    private Integer totalPrice;

    private UUID customer;

    private UUID seller;

    private List<ReadOrderItemResponseDTO> listOrderItem;
    
}
