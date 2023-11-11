package com.apapedia.catalog.restservice;

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
    public List<Catalog> getRestCatalogByPrice(Integer price) {
        return catalogDb.findByPriceAndIsDeletedFalse(price);
    }

    // Catalog #7
    @Override
    public List<Catalog> getRestCatalogByName(String name) {
        return catalogDb.findByProductNameContainingIgnoreCaseAndIsDeletedFalse(name);
    }

    @Override
    public List<Catalog> getAllCatalogSortedByPrice(Integer price, String sortOrder) {
        if ("asc".equalsIgnoreCase(sortOrder)) {
            return catalogDb.findByPriceAndIsDeletedFalseOrderByPriceAsc(price);
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            return catalogDb.findByPriceAndIsDeletedFalseOrderByPriceDesc(price);
        } else {
            throw new IllegalArgumentException("Invalid sortOrder value");
        }

    }

    @Override
    public List<Catalog> getAllCatalogSortedByName(String name, String sortOrder) {
        if ("asc".equalsIgnoreCase(sortOrder)) {
            return catalogDb.findByProductNameContainingIgnoreCaseAndIsDeletedFalseOrderByProductNameAsc(name);
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            return catalogDb.findByProductNameContainingIgnoreCaseAndIsDeletedFalseOrderByProductNameDesc(name);
        } else {
            throw new IllegalArgumentException("Invalid sortOrder value");
        }
    }
}
