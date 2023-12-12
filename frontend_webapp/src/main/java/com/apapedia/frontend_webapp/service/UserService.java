package com.apapedia.frontend_webapp.service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.apapedia.frontend_webapp.dto.request.WithdrawRequestDTO;
import com.apapedia.frontend_webapp.dto.response.CreateUserResponseDTO;
import com.apapedia.frontend_webapp.dto.response.UpdateUserResponseDTO;

public interface UserService {
    String getToken(String username, String name);
    CreateUserResponseDTO getUserDetails(UUID id, String token);
    UUID getUserIdFromToken(String token);
    String getUsernameFromToken(String token);
    void deleteUser(UUID id, String token);
    String withdraw(WithdrawRequestDTO withdrawRequestDTO, String token);
    String editUser(UUID id, String token, CreateUserResponseDTO requestBody);
}
