package com.apapedia.frontend_webapp.controller;

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

import com.apapedia.frontend_webapp.dto.request.CreateProductRequestDTO;
import com.apapedia.frontend_webapp.dto.response.ReadCategoryResponseDTO;

import jakarta.validation.Valid;

@Controller
public class CatalogController {
    @GetMapping("add-product")
    public String formAddProduct(Model model) {
        var productDTO = new CreateProductRequestDTO();
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
    public RedirectView addProduct(@Valid @ModelAttribute CreateProductRequestDTO productRequestDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder(); //Menginisiasi error message
            
            //Mengambil setiap error message yang ada
            for (FieldError error : bindingResult.getFieldErrors()) {
                String defaultMessage = error.getDefaultMessage();
                errorMessage.append(defaultMessage).append("<br>"); //Menampilkan error message dengan tampilan ke bawah
            }
            
            redirectAttributes.addFlashAttribute("productDTO", productRequestDTO);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return new RedirectView("/");
        }

        String uri = "http://localhost:8082/api/catalog/add";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CreateProductRequestDTO> res = restTemplate.postForEntity(uri, productRequestDTO, CreateProductRequestDTO.class);

        return new RedirectView("/");
    }
}
