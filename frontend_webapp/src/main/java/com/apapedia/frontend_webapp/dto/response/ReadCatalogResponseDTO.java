package com.apapedia.frontend_webapp.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadCatalogResponseDTO {
    private UUID id;

    private UUID seller;

    private Integer price;

    private String productName;

    private String productDescription;

	private ReadCategoryResponseDTO category;

    private Integer stock;

    private byte[] image;

    private Boolean isDeleted;
}
