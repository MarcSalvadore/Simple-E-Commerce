package com.apapedia.frontend_webapp.service;

import com.apapedia.frontend_webapp.dto.request.CreateUserRequestDTO;
import com.apapedia.frontend_webapp.dto.response.CreateUserResponseDTO;

public interface UserService {
    String getToken(String username, String password);
    CreateUserResponseDTO sendSeller(CreateUserRequestDTO userDTO, String jwtToken);
}
