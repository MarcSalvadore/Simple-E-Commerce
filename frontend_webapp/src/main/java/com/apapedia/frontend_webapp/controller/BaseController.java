package com.apapedia.frontend_webapp.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import com.apapedia.frontend_webapp.dto.response.ReadCatalogResponseDTO;
import com.apapedia.frontend_webapp.security.jwt.JwtUtils;
import com.apapedia.frontend_webapp.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BaseController {
    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

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
                String uri = "http://catalog-web:8082/api/catalog/viewall/" + sellerId;

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<ReadCatalogResponseDTO[]> res = restTemplate.getForEntity(uri,
                        ReadCatalogResponseDTO[].class);
                ReadCatalogResponseDTO[] listCatalog = res.getBody();

                model.addAttribute("imageLink", "http://apap-083.cs.ui.ac.id/api/image/");
                model.addAttribute("listCatalog", listCatalog);

            } 
        } else {
                String uri = "http://catalog-web:8082/api/catalog/viewall";
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<ReadCatalogResponseDTO[]> res = restTemplate.getForEntity(uri,
                        ReadCatalogResponseDTO[].class);
                ReadCatalogResponseDTO[] listCatalog = res.getBody();

                model.addAttribute("imageLink", "http://apap-083.cs.ui.ac.id/api/image/");
                model.addAttribute("listCatalog", listCatalog);
        }

        return "home";
    }
}
