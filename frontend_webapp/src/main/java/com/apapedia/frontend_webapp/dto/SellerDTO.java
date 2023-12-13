package com.apapedia.frontend_webapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

<<<<<<<< HEAD:frontend_webapp/src/main/java/com/apapedia/frontend_webapp/dto/SellerDTO.java
public class SellerDTO {
========
public class CreateUserRequestDTO {
>>>>>>>> 82f6558 (feat: update user):user/src/main/java/com/apapedia/user/dto/request/CreateUserRequestDTO.java
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