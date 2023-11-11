package com.apapedia.catalog.restservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.catalog.model.Category;
import com.apapedia.catalog.repository.CategoryDb;

@Service
public class CategoryRestServiceImpl implements CategoryRestService {
    @Autowired
    CategoryDb categoryDb;

    @Override
    public void createRestCategory(Category category) { categoryDb.save(category); }

    // Catalog #10
    @Override
    public List<Category> retrieveAllCategory() { return categoryDb.findAll(); }
}
