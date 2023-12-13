package com.apapedia.frontend_webapp.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import com.apapedia.frontend_webapp.dto.response.ReadCatalogResponseDTO;
import com.apapedia.frontend_webapp.security.jwt.JwtUtils;
import com.apapedia.frontend_webapp.security.xml.ServiceResponse;
import com.apapedia.frontend_webapp.service.UserService;
import com.apapedia.frontend_webapp.setting.Setting;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BaseController {
    private final WebClient webClient;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    public BaseController(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.SERVER_CATALOG_URL)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String jwtToken = (String) session.getAttribute("token");

            if (jwtUtils.validateToken(jwtToken)) {
                String username = userService.getUsernameFromToken(jwtToken);
                model.addAttribute("username", username);

                // Jika user login, dapatkan seller ID dari token
                UUID sellerId = userService.getUserIdFromToken(jwtToken);

                ReadCatalogResponseDTO[] listCatalog = this.webClient
                    .get()
                    .uri("/api/catalog/viewall/{id}", sellerId)
                    .retrieve()
                    .bodyToMono(ReadCatalogResponseDTO[].class)
                    .block();

                model.addAttribute("imageLink", Setting.SERVER_IMAGE_URL);
                model.addAttribute("listCatalog", listCatalog);
            } 
        } else {
            ReadCatalogResponseDTO[] listCatalog = this.webClient
                .get()
                .uri("/api/catalog/viewall")
                .retrieve()
                .bodyToMono(ReadCatalogResponseDTO[].class)
                .block();

            model.addAttribute("imageLink", Setting.SERVER_IMAGE_URL);
            model.addAttribute("listCatalog", listCatalog);
        }

        return "home";
    }
    
    @GetMapping("/catalog/search")
    public String searchCatalog(@RequestParam(name = "productName", required = false) String productName, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String jwtToken = (String) session.getAttribute("token");

            if (jwtUtils.validateToken(jwtToken)) {
                UUID sellerId = userService.getUserIdFromToken(jwtToken);

                ReadCatalogResponseDTO[] listCatalog = this.webClient
                    .get()
                    .uri("/api/catalog/search?query=" + productName + "&sellerId=" + sellerId)
                    .retrieve().bodyToMono(ReadCatalogResponseDTO[].class).block();

                model.addAttribute("listCatalog", listCatalog);
            }
        } else {
            ReadCatalogResponseDTO[] listCatalog = this.webClient
                .get()
                .uri("/api/catalog/search?query=" + productName)
                .retrieve().bodyToMono(ReadCatalogResponseDTO[].class).block();

            model.addAttribute("listCatalog", listCatalog);
        }

        return "home";
    }
}
