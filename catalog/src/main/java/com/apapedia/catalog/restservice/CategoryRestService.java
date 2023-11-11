package com.apapedia.catalog.restservice;

import java.util.List;

import com.apapedia.catalog.model.Category;

public interface CategoryRestService {
    void createRestCategory(Category category);

    // Catalog #10
    List<Category> retrieveAllCategory();
}
