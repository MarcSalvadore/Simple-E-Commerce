package com.apapedia.frontend_webapp.controller;

import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import com.apapedia.frontend_webapp.dto.response.ReadOrderResponseDTO;

import jakarta.validation.Valid;
@Controller
public class OrderController {
    @GetMapping("/order/history")
    public String orderHistoryPage(@Valid @ModelAttribute UUID customerId, Model model) {
        String uri = "http://localhost:8083/api/order/customer/"+ customerId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ReadOrderResponseDTO[]> res = restTemplate.getForEntity(uri, ReadOrderResponseDTO[].class);
        ReadOrderResponseDTO[] listOrder = res.getBody();

        model.addAttribute("listOrder", listOrder);

        return "order/history";
    }

    
}
