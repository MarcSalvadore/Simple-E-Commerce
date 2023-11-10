package com.apapedia.catalog.restservice;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.catalog.model.Catalog;
import com.apapedia.catalog.repository.CatalogDb;
import com.github.javafaker.Cat;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CatalogRestServiceImpl implements CatalogRestService {
    @Autowired
    CatalogDb catalogDb;

    // @Transactional
    @Override
    public void createRestCatalog(Catalog catalog) { catalogDb.save(catalog); }

    @Override
    public List<Catalog> getRestAllCatalogBySellerId(UUID idSeller) {
        return catalogDb.findBySeller(idSeller);
    }

    @Override
    public Catalog getRestCatalogById(UUID id) {
        for (Catalog catalog : getAllCatalog()){
            if (catalog.getId().equals(id)){
                return catalog;
            }
        }
        return null;
    }

    @Override
    public List<Catalog> getAllCatalog() {
        return catalogDb.findAllByOrderByProductNameAsc();
    }

    @Override
    public Catalog updateRestCatalog(Catalog catalogFromDTO, Catalog newCatalog) {
        // ambil catalog lama
        Catalog catalog = getRestCatalogById(catalogFromDTO.getId());

        // set catalog baru ke catalog lama
        if (catalog != null){
            catalog.setProductName(newCatalog.getProductName());
            catalog.setPrice(newCatalog.getPrice());
            catalog.setProductDescription(newCatalog.getProductDescription());
            catalog.setStock(newCatalog.getStock());
            catalog.setImage(newCatalog.getImage());
            catalog.setCategory(newCatalog.getCategory());

            catalogDb.save(catalog);
        }
        return catalog;
    }
}
