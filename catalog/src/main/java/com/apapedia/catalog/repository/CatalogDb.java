package com.apapedia.catalog.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apapedia.catalog.model.Catalog;

@Repository
public interface CatalogDb extends JpaRepository<Catalog, UUID> {
    List<Catalog> findBySellerAndIsDeletedFalse(UUID seller);
    List<Catalog> findByIsDeletedFalseOrderByProductNameAsc();
    List<Catalog> findByPriceAndIsDeletedFalse(Integer price);
    List<Catalog> findByProductNameContainingIgnoreCaseAndIsDeletedFalse(String name);

    List<Catalog> findByPriceAndIsDeletedFalseOrderByPriceAsc(Integer price);
    List<Catalog> findByPriceAndIsDeletedFalseOrderByPriceDesc(Integer price);

    List<Catalog> findByProductNameContainingIgnoreCaseAndIsDeletedFalseOrderByProductNameAsc(String name);
    List<Catalog> findByProductNameContainingIgnoreCaseAndIsDeletedFalseOrderByProductNameDesc(String name);



    // List<Catalog> findByIsDeletedFalseOrderByPriceAsc();
    // List<Catalog> findByIsDeletedFalseOrderByPriceDesc();


    // List<Catalog> findByIsDeletedFalseOrderByProductNameDesc();
}
