package com.apapedia.frontend_webapp.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserRequestDTO {
    @NotNull
    private UUID id;

    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;

    private String username;

    @NotBlank(message = "Email tidak boleh kosong")
    private String email;

    @NotBlank(message = "Harap masukkan password")
    private String password;

    @NotBlank(message = "Alamat tidak boleh kosong")
    private String address;
}
