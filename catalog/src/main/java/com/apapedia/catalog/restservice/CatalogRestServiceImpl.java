package com.apapedia.catalog.restservice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Override
    public void createRestCatalog(Catalog catalog) { catalogDb.save(catalog); }

    @Override
    @Transactional
    public List<Catalog> getRestAllCatalogBySellerId(UUID idSeller) {
        return catalogDb.findBySellerAndIsDeletedFalse(idSeller);
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
    @Transactional
    public List<Catalog> getAllCatalog() {
        return catalogDb.findByIsDeletedFalseOrderByProductNameAsc();
    }

    @Override
    public Catalog updateRestCatalog(Catalog catalogFromDTO) {
        // ambil catalog lama
        Catalog catalog = getRestCatalogById(catalogFromDTO.getId());

        // set catalog baru ke catalog lama
        if (catalog != null){
            catalog.setProductName(catalogFromDTO.getProductName());
            catalog.setPrice(catalogFromDTO.getPrice());
            catalog.setProductDescription(catalogFromDTO.getProductDescription());
            catalog.setStock(catalogFromDTO.getStock());
            catalog.setImage(catalogFromDTO.getImage());
            catalog.setCategory(catalogFromDTO.getCategory());
            catalog.setSeller(catalogFromDTO.getSeller());

            catalogDb.save(catalog);
        }

        return catalog;
    }

    @Override
    public void deleteRestCatalog(Catalog catalog) {
        catalog.setIsDeleted(true);
        catalog.setSeller(null);
        catalogDb.save(catalog);
    }

    // Catalog #8
    @Override
    @Transactional
    public List<Catalog> getRestCatalogByPrice(Integer priceMin, Integer priceMax) {
        return catalogDb.findByPriceBetweenAndIsDeletedFalse(priceMin, priceMax);
    }

    // Catalog #7
    @Override
    @Transactional
    public List<Catalog> getRestCatalogByName(String name) {
        return catalogDb.findByProductNameContainingIgnoreCaseAndIsDeletedFalseOrderByProductName(name);
    }

    @Override
    @Transactional
    public List<Catalog> getRestCatalogBySellerAndName(UUID sellerId, String name) {
        return catalogDb.findBySellerAndProductNameContainingIgnoreCaseAndIsDeletedFalseOrderByProductName(sellerId, name);
    }


    @Override
    @Transactional
    public List<Catalog> getCatalogListSorted(String sortBy, String sortOrder) {
        List<Catalog> catalogList = new ArrayList<>();

        if ("price".equalsIgnoreCase(sortBy)) {
            if ("asc".equalsIgnoreCase(sortOrder)) {
                catalogList = catalogDb.findByIsDeletedFalseOrderByPriceAsc();
            } else if ("desc".equalsIgnoreCase(sortOrder)) {
                catalogList = catalogDb.findByIsDeletedFalseOrderByPriceDesc();
            }
        } else if ("name".equalsIgnoreCase(sortBy)) {
            if ("asc".equalsIgnoreCase(sortOrder)) {
                catalogList = catalogDb.findByIsDeletedFalseOrderByProductNameAsc();
            } else if ("desc".equalsIgnoreCase(sortOrder)) {
                catalogList = catalogDb.findByIsDeletedFalseOrderByProductNameDesc();
            }
        }

        return catalogList;
    }
}
