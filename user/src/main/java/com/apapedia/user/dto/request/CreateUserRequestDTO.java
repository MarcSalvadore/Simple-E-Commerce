package com.apapedia.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CreateUserRequestDTO {
    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;

    @NotBlank(message = "Username tidak boleh kosong")
    private String username;

    @NotBlank(message = "Password tidak boleh kosong")
    private String password;

    @NotBlank(message = "Email tidak boleh kosong")
    private String email;

    @NotBlank(message = "Alamat tidak boleh kosong")
    private String address;
}
