package com.apapedia.user.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangePasswordRequestDTO {
    private String currentPassword;
    private String newPassword;
}
