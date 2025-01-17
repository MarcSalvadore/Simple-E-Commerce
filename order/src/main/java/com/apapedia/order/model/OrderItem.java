package com.apapedia.order.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item")
@JsonIgnoreProperties(value={"orderId"}, allowSetters = true)
public class OrderItem {
    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order orderId;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull
    @Column(name = "product_price", nullable = false)
    private Integer productPrice;
}
