package com.apapedia.frontend_webapp.dto.request;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.apapedia.frontend_webapp.dto.response.ReadCategoryResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCatalogRequestDTO extends CreateCatalogRequestDTO {
    private UUID seller;
}
