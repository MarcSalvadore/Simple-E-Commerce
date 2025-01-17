package com.apapedia.frontend_webapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.apapedia.frontend_webapp.dto.TokenDTO;
import com.apapedia.frontend_webapp.dto.request.LoginJwtRequestDTO;
import com.apapedia.frontend_webapp.dto.request.WithdrawRequestDTO;
import com.apapedia.frontend_webapp.dto.response.ChangePasswordResponseDTO;
import com.apapedia.frontend_webapp.dto.response.CreateUserResponseDTO;
import com.apapedia.frontend_webapp.security.jwt.JwtUtils;
import com.apapedia.frontend_webapp.setting.Setting;

import io.jsonwebtoken.Claims;

@Service
public class UserServiceImpl implements UserService {
    private final WebClient webClient;
    
    @Autowired
    JwtUtils jwtService;

    public UserServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.SERVER_USER_URL)
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

    @Override
    public void deleteUser(UUID id, String token) {
        this.webClient
        .delete()
        .uri("/api/user/{id}/delete", id)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .retrieve()
        .bodyToMono(Void.class)
        .block();
    }

    @Override
    public String withdraw(WithdrawRequestDTO withdrawRequestDTO, String token) {
        var response = this.webClient
            .post()
            .uri("/api/withdraw")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(withdrawRequestDTO)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        
        if (response != null) {
            return "Withdraw telah berhasil!";
        }

        return "Withdraw gagal";
    }

    @Override
    public CreateUserResponseDTO editUser(UUID id, String token, CreateUserResponseDTO requestBody) {
        var response = this.webClient
            .put()
            .uri("/api/user/{id}/update", id)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .body(BodyInserters.fromValue(requestBody))
            .retrieve()
            .bodyToMono(CreateUserResponseDTO.class)
            .block();
        return response;
    }

    @Override
    public void changePassword(UUID id, String token, ChangePasswordResponseDTO changePasswordResponseDTO) {
        this.webClient
                .put()
                .uri("/api/user/{id}/change-password", id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromValue(changePasswordResponseDTO))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
