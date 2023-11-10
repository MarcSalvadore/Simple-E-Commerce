package com.apapedia.user.dto.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartResponseDTO {
    private UUID id;

    private UUID userId;

    private Integer totalPrice;
}
