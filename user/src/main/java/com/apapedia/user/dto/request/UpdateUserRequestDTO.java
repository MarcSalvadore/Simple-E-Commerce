package com.apapedia.user.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserRequestDTO {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String address;
}
