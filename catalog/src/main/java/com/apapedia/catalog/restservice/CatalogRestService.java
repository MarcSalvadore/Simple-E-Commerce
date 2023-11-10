package com.apapedia.catalog.restservice;

import java.util.List;
import java.util.UUID;

import com.apapedia.catalog.model.Catalog;
import com.github.javafaker.Cat;

public interface CatalogRestService {
    void createRestCatalog(Catalog catalog);

    List<Catalog> getRestAllCatalogBySellerId(UUID idSeller);

    List<Catalog> getAllCatalog();

    Catalog getRestCatalogById(UUID id);

    Catalog updateRestCatalog(Catalog catalogFromDTO, Catalog newCatalog);
}
