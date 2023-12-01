package com.apapedia.frontend_webapp.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponseDTO {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String address;
}
