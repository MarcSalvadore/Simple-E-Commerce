package com.apapedia.frontend_webapp.dto.request;

import java.util.UUID;

import com.apapedia.frontend_webapp.dto.response.ReadCategoryResponseDTO;

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
public class CreateCatalogRequestDTO {
    private UUID seller;

    @NotBlank(message = "Nama Produk tidak boleh kosong")
    private String productName;

    @Positive(message = "Harga harus lebih dari 0")
    @NotNull(message = "Harga Barang tidak boleh kosong")
    private Integer price;

    @NotBlank(message = "Deskripsi Produk tidak boleh kosong")
    private String productDescription;

    // @NotBlank(message = "Harap unggah Gambar Produk")
    private String image;
    
    @NotNull
    private ReadCategoryResponseDTO category;

    @NotNull(message = "Stok Produk tidak boleh ksoong")
    @PositiveOrZero(message = "Stok harus positif")
    private Integer stock;
    
}
