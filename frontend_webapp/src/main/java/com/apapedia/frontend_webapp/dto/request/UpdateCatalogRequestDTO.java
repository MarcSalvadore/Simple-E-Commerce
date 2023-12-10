package com.apapedia.frontend_webapp.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCatalogRequestDTO extends CreateCatalogRequestDTO {
    private UUID seller;
}
