package com.apapedia.frontend_webapp.dto.request;

import org.hibernate.validator.constraints.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProductRequestDTO {
    private UUID seller;
    
    @NotBlank(message = "Nama produk tidak boleh kosong")
    private String productName;

    @PositiveOrZero(message = "Harga harus minimal 1.0 atau lebih")
    private Integer price;

    @NotBlank(message = "Deskripsi produk tidak boleh kosong")
    private String productDescription;

    @PositiveOrZero(message = "Harga harus minimal 1.0 atau lebih")
    private Integer stock;

    @NotBlank(message = "Masukkan gambar produk")
    private String image;

    @NotBlank(message = "Pilih kategori dari produk")
    private String category;
    
}
