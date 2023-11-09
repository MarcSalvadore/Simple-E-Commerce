package com.apapedia.catalog.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.catalog.model.Catalog;
import com.apapedia.catalog.repository.CatalogDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CatalogRestServiceImpl implements CatalogRestService {
    @Autowired
    CatalogDb catalogDb;

    // @Transactional
    @Override
    public void createRestCatalog(Catalog catalog) { catalogDb.save(catalog); }
}
