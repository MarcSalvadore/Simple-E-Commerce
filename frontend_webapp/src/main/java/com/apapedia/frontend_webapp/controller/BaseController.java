package com.apapedia.frontend_webapp.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import com.apapedia.frontend_webapp.dto.response.ReadCatalogResponseDTO;
import com.apapedia.frontend_webapp.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BaseController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home(@CookieValue(name = "token", required = false) String token, Model model, HttpServletRequest request) {
        boolean isTokenPresent = containsCookie(request, "token");
        model.addAttribute("isTokenPresent", isTokenPresent);

        if (isTokenPresent) {
            String username = userService.getUsernameFromToken(token);
            model.addAttribute("username", username);
        }
        String uri = "http://localhost:8082/api/catalog/viewall";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ReadCatalogResponseDTO[]> res = restTemplate.getForEntity(uri, ReadCatalogResponseDTO[].class);
        ReadCatalogResponseDTO[] listCatalog = res.getBody();

        model.addAttribute("imageLink", "http://localhost:8082/api/image/");
        model.addAttribute("listCatalog", listCatalog);

        return "home";
    }

    // Utility method to check if a cookie is present
    private boolean containsCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        return cookies != null && Arrays.stream(cookies).anyMatch(cookie -> cookie.getName().equals(cookieName));
    }
}
