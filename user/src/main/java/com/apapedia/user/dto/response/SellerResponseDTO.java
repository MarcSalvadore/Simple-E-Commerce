package com.apapedia.user.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SellerResponseDTO {
    private String name;

    private String username;

    private String password;

    private String email;

    private String address;
    
}