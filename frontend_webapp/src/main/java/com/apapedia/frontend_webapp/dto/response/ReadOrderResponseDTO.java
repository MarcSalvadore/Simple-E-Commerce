package com.apapedia.frontend_webapp.dto.response;

import org.hibernate.validator.constraints.UUID;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ReadOrderResponseDTO {
    
    private String id;

    // private Date createdAt;

    // private Date updatedAt;

    private Integer status;

    private Integer totalPrice;
 
    // @JsonDeserialize(using = UUIDDeserializer.class)
    // @UUID
    // private String customer;

    // @JsonDeserialize(using = UUIDDeserializer.class)
    // @UUID
    private String seller;

    private List<ReadOrderItemResponseDTO> listOrderItem;
    
}
