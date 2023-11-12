package com.apapedia.frontend_webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.apapedia.frontend_webapp.dto.request.CreateProductRequestDTO;
import com.apapedia.frontend_webapp.dto.response.ReadCatalogResponseDTO;
import com.apapedia.frontend_webapp.dto.response.ReadCategoryResponseDTO;

import jakarta.validation.Valid;

@Controller
public class BaseController {
    @GetMapping("/")
    public String home(Model model) {
 
        String uri = "http://localhost:8082/api/catalog/viewall";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ReadCatalogResponseDTO[]> res = restTemplate.getForEntity(uri, ReadCatalogResponseDTO[].class);
        ReadCatalogResponseDTO[] listCatalog = res.getBody();

        model.addAttribute("listCatalog", listCatalog);

        return "home";
    }
}
