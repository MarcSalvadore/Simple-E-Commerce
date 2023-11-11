package com.apapedia.frontend_webapp.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProductRequestDTO {
    @NotBlank(message = "Nama produk tidak boleh kosong")
    private String name;

    @DecimalMin(value = "1.0", inclusive = true, message = "Harga harus minimal 1.0 atau lebih")
    private BigDecimal price;

    @NotBlank(message = "Deskripsi produk tidak boleh kosong")
    private String description;

    @NotNull(message = "Stok produk tidak boleh kosong")
    private int stock;

    @NotBlank(message = "Masukkan gambar produk")
    private String image;

    @NotBlank(message = "Pilih kategori dari produk")
    private String category;
    
}
