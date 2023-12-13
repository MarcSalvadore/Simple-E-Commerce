package com.apapedia.frontend_webapp.dto.response;

import java.util.List;

import lombok.Data;

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
