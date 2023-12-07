package com.apapedia.frontend_webapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.apapedia.frontend_webapp.dto.TokenDTO;
import com.apapedia.frontend_webapp.dto.request.LoginJwtRequestDTO;
import com.apapedia.frontend_webapp.dto.response.CreateUserResponseDTO;
import com.apapedia.frontend_webapp.security.jwt.JwtUtils;

import io.jsonwebtoken.Claims;

@Service
public class UserServiceImpl implements UserService {
    private final WebClient webClient;
    
    @Autowired
    JwtUtils jwtService;

    public UserServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
    }

    @Override
    public String getToken(String username, String name) {
        var body = new LoginJwtRequestDTO(username, name);

        var response = this.webClient
            .post()
            .uri("/api/auth/login-seller")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(TokenDTO.class)
            .block();
        
        var token = response.getToken();
        return token;
    }

    @Override
    public CreateUserResponseDTO getUserDetails(UUID id, String token) {
        CreateUserResponseDTO response = this.webClient
            .get()
            .uri("/api/user/{id}", id)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .retrieve()
            .bodyToMono(CreateUserResponseDTO.class)
            .block();
        return response;
    }

    @Override
    public UUID getUserIdFromToken(String token) {
        Claims claims = jwtService.decodeToken(token);
        return UUID.fromString(claims.get("userId", String.class));
    }

    @Override
    public String getUsernameFromToken(String token) {
        Claims claims = jwtService.decodeToken(token);
        return claims.get("sub", String.class);
    }
}
