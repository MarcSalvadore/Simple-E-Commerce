package com.apapedia.user.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadWithdrawResponseDTO {
    private UUID idSeller;

    private Long balance;

    private Long amount;
}