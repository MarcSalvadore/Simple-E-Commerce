package com.apapedia.frontend_webapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.apapedia.frontend_webapp.dto.TokenDTO;
import com.apapedia.frontend_webapp.dto.request.CreateUserRequestDTO;
import com.apapedia.frontend_webapp.dto.request.LoginJwtRequestDTO;
import com.apapedia.frontend_webapp.dto.response.CreateUserResponseDTO;
import com.apapedia.frontend_webapp.dto.response.LoginJwtResponseDTO;
import com.apapedia.frontend_webapp.security.jwt.JwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import reactor.core.publisher.Mono;

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
    public String getToken(String username, String password) {
        var body = new LoginJwtRequestDTO(username, password);

        var response = this.webClient
            .post()
            .uri("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(TokenDTO.class)
            .block();
        
        var token = response.getToken();
        return token;
    }

    @Override
    public CreateUserResponseDTO sendSeller(CreateUserRequestDTO userDTO, String jwtToken) {
        try {
            var response = this.webClient
                .post()
                .uri("/api/seller/create")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userDTO)
                .retrieve()
                .bodyToMono(CreateUserResponseDTO.class);
            var userSubmitted = response.block();
            return userSubmitted;  
        }
        catch (Exception e) {
            CreateUserResponseDTO userResponseDTO = new CreateUserResponseDTO();
            return userResponseDTO;
        }
    }

    @Override
    public String login(String username, String password) {
        LoginJwtRequestDTO loginData = new LoginJwtRequestDTO();
        loginData.setUsername(username);
        loginData.setPassword(password);

        var response = this.webClient.post()
                .uri("/api/login")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(loginData))
                .retrieve()
                .bodyToMono(LoginJwtResponseDTO.class)
                .map(LoginJwtResponseDTO::getToken)
                .block();
        return response;
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
    public void logout() {
        var response = this.webClient.post()
            .uri("/api/logout")
            .retrieve()
            .toBodilessEntity()
            .subscribe(
                responseEntity -> {
                    // Logout was successful
                    System.out.println("Logout successful");
                },
                error -> {
                    // Handle logout failure
                    System.err.println("Logout failed: " + error.getMessage());
                }
            );
    }

    @Override
    public String getUsernameFromToken(String token) {
        Claims claims = jwtService.decodeToken(token);
        return claims.get("sub", String.class);
    }
    
    // @Override
    // public String login(String username, String password){
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_JSON);

    //     LoginJwtRequestDTO loginJwtRequestDTO = new LoginJwtRequestDTO();
    //     loginJwtRequestDTO.setUsername(username);
    //     loginJwtRequestDTO.setPassword(password);

    //     String token = webClient.post()
    //             .uri(apiUrl)
    //             .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    //             .body(BodyInserters.fromValue(loginData))
    //             .retrieve()
    //             .bodyToMono(LoginJwtResponseDTO.class)
    //             .block()
    //             .getToken();

    // }
}
