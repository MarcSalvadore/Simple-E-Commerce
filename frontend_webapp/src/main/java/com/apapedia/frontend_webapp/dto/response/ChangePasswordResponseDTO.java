package com.apapedia.frontend_webapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordResponseDTO {
    private String currentPassword;
    private String newPassword;
}
