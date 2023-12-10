package com.apapedia.frontend_webapp.dto.request;

import com.github.javafaker.Bool;

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
    private String category;   
    private Boolean isDeleted = false;
}