package com.apapedia.catalog.dto.request;

import java.util.UUID;

import com.apapedia.catalog.model.Category;

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
    @NotNull
    private UUID seller;

    @NotBlank(message = "Nama Produk tidak boleh kosong")
    private String productName;

    @Positive(message = "Harga harus lebih dari 0")
    @NotNull(message = "Harga Barang tidak boleh kosong")
    private Integer price;

    @NotBlank(message = "Deskripsi Produk tidak boleh kosong")
    private String productDescription;

    @NotNull(message = "Harap unggah Gambar Produk")
    private byte[] image;
    
    @NotNull
    private Category category;

    @NotNull(message = "Stok Produk tidak boleh ksoong")
    @PositiveOrZero(message = "Stok harus positif")
    private Integer stock;
}
