package com.apapedia.frontend_webapp.dto.request;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.apapedia.frontend_webapp.dto.response.ReadCategoryResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class UpdateCatalogRequestDTO{
    private UUID id;

    private UUID seller;
    
    @NotBlank(message = "Nama Produk tidak boleh kosong")
    private String productName;

    @Positive(message = "Harga harus lebih dari 0")
    @NotNull(message = "Harga Barang tidak boleh kosong")
    private Integer price;

    @NotBlank(message = "Deskripsi Produk tidak boleh kosong")
    private String productDescription;

    @JsonIgnore
    @NotNull
    private MultipartFile imageFile;

    // @NotNull(message = "Harap unggah Gambar Produk")
    private byte[] image;
    
    @NotNull
    private ReadCategoryResponseDTO category;

    @NotNull(message = "Stok Produk tidak boleh ksoong")
    @PositiveOrZero(message = "Stok harus positif")
    private Integer stock;
}
