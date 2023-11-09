package com.apapedia.catalog.dto;

import org.mapstruct.Mapper;

import com.apapedia.catalog.dto.request.CreateCatalogRequestDTO;
import com.apapedia.catalog.model.Catalog;

@Mapper(componentModel = "spring")
public interface CatalogMapper {
    Catalog createCatalogRequestDTOToCatalog(CreateCatalogRequestDTO createCatalogRequestDTO);
}