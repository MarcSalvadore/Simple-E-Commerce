package com.apapedia.user.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserResponseDTO {
    private UUID id;
    private String name;
    private String username;
    private String password;
    private String address;
    private String email;
}
