package com.apapedia.frontend_webapp.service;

import java.util.UUID;

import com.apapedia.frontend_webapp.dto.request.WithdrawRequestDTO;
import com.apapedia.frontend_webapp.dto.response.ChangePasswordResponseDTO;
import com.apapedia.frontend_webapp.dto.response.CreateUserResponseDTO;

public interface UserService {
    String getToken(String username, String name);
    CreateUserResponseDTO getUserDetails(UUID id, String token);
    UUID getUserIdFromToken(String token);
    String getUsernameFromToken(String token);
    void deleteUser(UUID id, String token);
    String withdraw(WithdrawRequestDTO withdrawRequestDTO, String token);
    CreateUserResponseDTO editUser(UUID id, String token, CreateUserResponseDTO createUserResponseDTO);
    void changePassword(UUID id, String token, ChangePasswordResponseDTO changePasswordResponseDTO);
}
