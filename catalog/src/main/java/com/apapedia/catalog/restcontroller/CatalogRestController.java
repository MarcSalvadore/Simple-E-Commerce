package com.apapedia.catalog.restcontroller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.catalog.dto.CatalogMapper;
import com.apapedia.catalog.dto.request.CreateCatalogRequestDTO;
import com.apapedia.catalog.model.Catalog;
import com.apapedia.catalog.restservice.CatalogRestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CatalogRestController {
    @Autowired
    CatalogMapper catalogMapper;

    @Autowired
    CatalogRestService catalogRestService;

    @PostMapping(value = "/catalog/add")
    public Catalog restAddCatalog(@Valid @RequestBody CreateCatalogRequestDTO catalogDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, bindingResult.getFieldErrors().get(0).getDefaultMessage()
            );
        } else {
            var catalog = catalogMapper.createCatalogRequestDTOToCatalog(catalogDTO);
            catalogRestService.createRestCatalog(catalog);
            return catalog;
        }
    }

    @GetMapping(value = "/catalog/viewall/{idSeller}")
    public List<Catalog> getAllCatalogBySellerId(@PathVariable("idSeller") UUID idSeller){
        return catalogRestService.getRestAllCatalogBySellerId(idSeller);
    }

}
