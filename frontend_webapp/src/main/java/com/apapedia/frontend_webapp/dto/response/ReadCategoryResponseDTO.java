package com.apapedia.frontend_webapp.dto.response;

import java.util.UUID;

import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Configuration
public class ReadCategoryResponseDTO {
    private UUID id;
    private String name;
}
