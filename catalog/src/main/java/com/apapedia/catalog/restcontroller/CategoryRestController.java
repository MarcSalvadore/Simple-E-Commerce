package com.apapedia.catalog.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apapedia.catalog.model.Category;
import com.apapedia.catalog.restservice.CategoryRestService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class CategoryRestController {
    @Autowired
    CategoryRestService categoryRestService;

    // Catalog #10
    @Transactional
    @GetMapping(value = "/category/viewall")
    public List<Category> getAllCategory() { return categoryRestService.retrieveAllCategory(); }
}
