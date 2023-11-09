package com.apapedia.catalog.restservice;

import java.util.List;
import java.util.UUID;

import com.apapedia.catalog.model.Catalog;

public interface CatalogRestService {
    void createRestCatalog(Catalog catalog);

    List<Catalog> getRestAllCatalogBySellerId(UUID idSeller);
}
