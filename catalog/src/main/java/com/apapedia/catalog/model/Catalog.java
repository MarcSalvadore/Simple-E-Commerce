package com.apapedia.catalog.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "catalog")
@JsonIgnoreProperties(value={"image"}, allowSetters = true)
public class Catalog {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(name = "seller_id")
    private UUID seller;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull
    @Column(name = "product_description", nullable = false)
    private String productDescription;

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private Category category;

    @NotNull
    @PositiveOrZero
    @Column(name = "stock", nullable = false)
    private Integer stock;

    // @NotNull
    @Column(name = "image", nullable = false)
    @Lob
    private byte[] image;

    // Atribut untuk soft delete catalog
    @NotNull
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}
