package com.apapedia.user.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCustomerRequestDTO {
    private String username;
    private String password;
}
