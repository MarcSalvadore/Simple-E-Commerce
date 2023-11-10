package com.apapedia.catalog.restcontroller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.catalog.dto.CatalogMapper;
import com.apapedia.catalog.dto.request.CreateCatalogRequestDTO;
import com.apapedia.catalog.dto.request.UpdateCatalogRequestDTO;
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

    @GetMapping(value = "/catalog/viewall")
    public List<Catalog> retrieveAllCatalog() { return catalogRestService.getAllCatalog(); }

    @GetMapping(value = "/catalog/detail/{idCatalog}")
    public Catalog getCatalog(@PathVariable("idCatalog") UUID idCatalog){
        try{
            return catalogRestService.getRestCatalogById(idCatalog);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id Catalog " + idCatalog + " not found"
            );
        }
    }

    @PutMapping(value = "catalog/update")
    private Catalog updateRestCatalog(@Valid @RequestBody UpdateCatalogRequestDTO catalogDTO, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field");      
        } else{
            Catalog catalogFromDto = catalogMapper.updateCatalogRequestDTOToCatalog(catalogDTO);
            Catalog catalog = catalogRestService.updateRestCatalog(catalogFromDto);
            return catalog;
        }

    }

    @DeleteMapping("catalog/{id}")
    public ResponseEntity<String> deleteCatalog(@PathVariable("id") UUID id) {
        //Mendapatkan object Catalog yang akan dihapus
        var catalog = catalogRestService.getRestCatalogById(id);
        //Soft delete Catalog dari database
        catalogRestService.deleteRestCatalog(catalog);

        //Mengembalikan response pesan
        return ResponseEntity.ok("Catalog has been deleted");
    }
}
