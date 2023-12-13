package com.apapedia.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserRequestDTO {
    private String name;
    private String username;
    private String password;
    private String email;
    private String address;
    @NotBlank(message = "Kategori tidak boleh kosong")
    private String category;
}
