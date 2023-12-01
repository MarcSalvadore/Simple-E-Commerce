package com.apapedia.frontend_webapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.apapedia.frontend_webapp.dto.TokenDTO;
import com.apapedia.frontend_webapp.dto.request.CreateUserRequestDTO;
import com.apapedia.frontend_webapp.dto.request.LoginJwtRequestDTO;
import com.apapedia.frontend_webapp.dto.response.CreateUserResponseDTO;

@Service
public class UserServiceImpl implements UserService {
    private final WebClient webClient;

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
}
