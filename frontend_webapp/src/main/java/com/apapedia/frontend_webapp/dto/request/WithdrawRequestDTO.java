package com.apapedia.frontend_webapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class WithdrawRequestDTO {
    private UUID idSeller;
    private Long balance;

    @NotNull(message = "Jumlah uang tidak boleh kosong")
    @Positive(message = "Jumlah uang harus lebih dari 0")
    private Long amount;
}
