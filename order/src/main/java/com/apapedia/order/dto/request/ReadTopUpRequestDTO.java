package com.apapedia.order.dto.request;

import java.util.UUID;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadTopUpRequestDTO {
    private UUID sellerId;
    private Long amount;
}
