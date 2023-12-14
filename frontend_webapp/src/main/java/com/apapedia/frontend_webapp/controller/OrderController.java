package com.apapedia.frontend_webapp.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.apapedia.frontend_webapp.dto.request.UpdateOrderStatusRequestDTO;
import com.apapedia.frontend_webapp.dto.response.ReadOrderResponseDTO;
import com.apapedia.frontend_webapp.security.jwt.JwtUtils;
import com.apapedia.frontend_webapp.service.UserService;
import com.apapedia.frontend_webapp.setting.Setting;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;
    
    @GetMapping("/order/history/{sellerId}")
    public String orderHistoryPage(@PathVariable UUID sellerId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String jwtToken = (String) session.getAttribute("token");

            if (jwtUtils.validateToken(jwtToken)) {
                String username = userService.getUsernameFromToken(jwtToken);
                model.addAttribute("username", username);
            }
        }
        
        String uri = Setting.SERVER_ORDER_URL + "/api/order/seller/" + sellerId;
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
        String uri = Setting.SERVER_ORDER_URL + "/api/order/status/" + orderId;
        UpdateOrderStatusRequestDTO request = new UpdateOrderStatusRequestDTO();
        request.setStatus(status);
        
        RestTemplate restTemplate = new RestTemplate();

        // Create HttpEntity with the request body
        HttpEntity<UpdateOrderStatusRequestDTO> requestEntity = new HttpEntity<>(request);

        // Make the PUT request
        ResponseEntity<String> res = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, String.class);

        // Get the response body
        String responseBody = res.getBody();

        String uriGet = Setting.SERVER_ORDER_URL + "/api/order/seller/" + sellerId;
        ParameterizedTypeReference<List<ReadOrderResponseDTO>> responseType = new ParameterizedTypeReference<List<ReadOrderResponseDTO>>() {};
        ResponseEntity<List<ReadOrderResponseDTO>> resp = restTemplate.exchange(uriGet, HttpMethod.GET, null, responseType);
        List<ReadOrderResponseDTO> listOrder = resp.getBody();

        model.addAttribute("listOrder", listOrder);

        return "order/history";
    }

    
}
