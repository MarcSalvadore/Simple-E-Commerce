package com.apapedia.order.dto.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class SellerResponseDTO {
    private UUID sellerId;
    private Long amount;
}
