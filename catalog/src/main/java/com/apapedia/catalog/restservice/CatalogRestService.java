package com.apapedia.catalog.restservice;

import java.util.List;
import java.util.UUID;

import com.apapedia.catalog.model.Catalog;

public interface CatalogRestService {
    void createRestCatalog(Catalog catalog);

    List<Catalog> getRestAllCatalogBySellerId(UUID idSeller);

    List<Catalog> getAllCatalog();

    Catalog getRestCatalogById(UUID id);

    Catalog updateRestCatalog(Catalog catalogFromDTO);

    void deleteRestCatalog(Catalog catalog);

    // Catalog #8
    List<Catalog> getRestCatalogByPrice(Integer price);
}
