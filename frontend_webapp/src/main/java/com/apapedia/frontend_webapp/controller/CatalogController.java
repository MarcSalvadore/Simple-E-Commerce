package com.apapedia.frontend_webapp.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.result.view.RedirectView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apapedia.frontend_webapp.dto.request.CreateCatalogRequestDTO;
import com.apapedia.frontend_webapp.dto.response.ReadCategoryResponseDTO;

import jakarta.validation.Valid;

@Controller
public class CatalogController {
    @GetMapping("add-product")
    public String formAddProduct(Model model) {
        var productDTO = new CreateCatalogRequestDTO();
        String uri = "http://localhost:8082/api/category/viewall";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ReadCategoryResponseDTO[]> res = restTemplate.getForEntity(uri, ReadCategoryResponseDTO[].class);
        ReadCategoryResponseDTO[] listCategory = res.getBody();

        model.addAttribute("productDTO", productDTO);
        model.addAttribute("listCategory", listCategory);
        return "catalog/form-add-product";
    }
    
    // masih error
    @PostMapping("add-product")
    public RedirectView addProduct(@Valid @ModelAttribute CreateCatalogRequestDTO productRequestDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException{
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder(); //Menginisiasi error message
            
            //Mengambil setiap error message yang ada
            for (FieldError error : bindingResult.getFieldErrors()) {
                String defaultMessage = error.getDefaultMessage();
                errorMessage.append(defaultMessage).append("<br>"); //Menampilkan error message dengan tampilan ke bawah
            }
            
            redirectAttributes.addFlashAttribute("productDTO", productRequestDTO);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return new RedirectView("catalog/form-add-product");
        }

        productRequestDTO.setSeller(UUID.randomUUID());
        productRequestDTO.setImage(productRequestDTO.getImageFile().getBytes());

        String uri = "http://localhost:8082/api/catalog/add";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CreateCatalogRequestDTO> res = restTemplate.postForEntity(uri, productRequestDTO, CreateCatalogRequestDTO.class);

        redirectAttributes.addFlashAttribute("productDTO", productRequestDTO);
        return new RedirectView("/");
    }
}