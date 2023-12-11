package com.apapedia.order.dto.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class TopOrderResponseDTO {
    private UUID productId;
    private String productName;
    private Long quantity;
    private String url;
}
