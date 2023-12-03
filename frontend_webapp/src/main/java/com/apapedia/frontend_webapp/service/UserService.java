package com.apapedia.frontend_webapp.service;

import java.util.UUID;

import com.apapedia.frontend_webapp.dto.response.CreateUserResponseDTO;

public interface UserService {
    String getToken(String username, String password);
    CreateUserResponseDTO getUserDetails(UUID id, String token);
    UUID getUserIdFromToken(String token);
    String getUsernameFromToken(String token);
}
