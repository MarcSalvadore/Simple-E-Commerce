package com.apapedia.user.dto.response;
import org.hibernate.validator.constraints.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponseDTO {
    private UUID id;
    private String username;
    private String name;
    private String role;
}
