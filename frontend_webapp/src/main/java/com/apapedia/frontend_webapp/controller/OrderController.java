package com.apapedia.frontend_webapp.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.apapedia.frontend_webapp.dto.request.UpdateOrderStatusRequestDTO;
import com.apapedia.frontend_webapp.dto.response.ReadOrderResponseDTO;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
    
    @GetMapping("/order/history/{sellerId}")
    public String orderHistoryPage(@PathVariable UUID sellerId, Model model) {
        String uri = "http://order-web:8083/api/order/seller/" + sellerId;
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<ReadOrderResponseDTO>> responseType = new ParameterizedTypeReference<List<ReadOrderResponseDTO>>() {};
        ResponseEntity<List<ReadOrderResponseDTO>> res = restTemplate.exchange(uri, HttpMethod.GET, null, responseType);
        List<ReadOrderResponseDTO> listOrder = res.getBody();

        model.addAttribute("listOrder", listOrder);

        return "order/history";
    }

    @PostMapping(value = "/order/status/{orderId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String putMethodName(@PathVariable UUID orderId, @RequestParam("status") int status, @RequestParam("sellerId") String sellerId, Model model) {
        //TODO: process PUT request 
        String uri = "http://order-web:8083/api/order/status/" + orderId;
        UpdateOrderStatusRequestDTO request = new UpdateOrderStatusRequestDTO();
        request.setStatus(status);
        
        RestTemplate restTemplate = new RestTemplate();

        // Create HttpEntity with the request body
        HttpEntity<UpdateOrderStatusRequestDTO> requestEntity = new HttpEntity<>(request);

        // Make the PUT request
        ResponseEntity<String> res = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, String.class);

        // Get the response body
        String responseBody = res.getBody();

        String uriGet = "http://order-web:8083/api/order/seller/" + sellerId;
        ParameterizedTypeReference<List<ReadOrderResponseDTO>> responseType = new ParameterizedTypeReference<List<ReadOrderResponseDTO>>() {};
        ResponseEntity<List<ReadOrderResponseDTO>> resp = restTemplate.exchange(uriGet, HttpMethod.GET, null, responseType);
        List<ReadOrderResponseDTO> listOrder = resp.getBody();

        model.addAttribute("listOrder", listOrder);

        return "order/history";
    }

    
}
