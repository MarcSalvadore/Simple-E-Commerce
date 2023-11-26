package com.apapedia.user.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    private String username;
    private String password;
    private String email;
    private String address;
}
