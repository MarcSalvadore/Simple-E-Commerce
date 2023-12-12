package com.apapedia.frontend_webapp.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.apapedia.frontend_webapp.dto.response.CreateUserResponseDTO;
import com.apapedia.frontend_webapp.dto.response.ReadCatalogResponseDTO;
import com.apapedia.frontend_webapp.security.jwt.JwtUtils;
import com.apapedia.frontend_webapp.service.UserService;
import com.apapedia.frontend_webapp.setting.Setting;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BaseController {
    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    private WebClient webClient = WebClient.builder()
                    .codecs(configurer -> configurer.defaultCodecs()
                    .jaxb2Decoder(new Jaxb2XmlDecoder()))
                    .build();

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String jwtToken = (String) session.getAttribute("token");
            System.out.println(jwtToken);

            if (jwtUtils.validateToken(jwtToken)) {
                String username = userService.getUsernameFromToken(jwtToken);
                model.addAttribute("username", username);

                // Jika user login, dapatkan seller ID dari token
                UUID sellerId = userService.getUserIdFromToken(jwtToken);
                // endpoint viewall catalog by seller id
                ReadCatalogResponseDTO[] response = this.webClient
                    .get()
                    .uri("/api/catalog/viewall/{id}", sellerId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                    .retrieve()
                    .bodyToMono(ReadCatalogResponseDTO[].class)
                    .block();

                model.addAttribute("imageLink", Setting.SERVER_IMAGE_URL);
                model.addAttribute("listCatalog", response);
            } 
        } else {
                String uri = Setting.SERVER_CATALOG_URL + "/api/catalog/viewall";
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<ReadCatalogResponseDTO[]> res = restTemplate.getForEntity(uri,
                        ReadCatalogResponseDTO[].class);
                ReadCatalogResponseDTO[] listCatalog = res.getBody();

                model.addAttribute("imageLink", Setting.SERVER_IMAGE_URL);
                model.addAttribute("listCatalog", listCatalog);
        }

        return "home";
    }
}
