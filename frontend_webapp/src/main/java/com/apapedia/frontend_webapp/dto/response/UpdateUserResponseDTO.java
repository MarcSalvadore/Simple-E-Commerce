package com.apapedia.frontend_webapp.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserResponseDTO {
    private UUID id;
    private String name;
    private String username;
    // private String password;
    private String email;
    private String address;
    // private String category;
    // private String role;
}
