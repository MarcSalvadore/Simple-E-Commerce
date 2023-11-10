package com.apapedia.catalog.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCatalogRequestDTO extends CreateCatalogRequestDTO{
    private UUID id;
}
