package com.apapedia.frontend_webapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import com.apapedia.frontend_webapp.dto.response.ReadCatalogResponseDTO;

@Controller
public class BaseController {
    @GetMapping("/")
    public String home(Model model) {
 
        String uri = "http://localhost:8082/api/catalog/viewall";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ReadCatalogResponseDTO[]> res = restTemplate.getForEntity(uri, ReadCatalogResponseDTO[].class);
        ReadCatalogResponseDTO[] listCatalog = res.getBody();

        model.addAttribute("imageLink", "http://localhost:8082/api/image/");
        model.addAttribute("listCatalog", listCatalog);

        return "home";
    }
}
