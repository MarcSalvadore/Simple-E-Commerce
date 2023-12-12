package com.apapedia.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserResponseDTO {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String address;
}