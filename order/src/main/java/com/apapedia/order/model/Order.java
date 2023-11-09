package com.apapedia.order.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "orders")
public class Order {
    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Date createdAt = new Date();

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt = new Date();

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status = 0;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @NotNull
    @Column(name = "customer", nullable = false)
    private UUID customer;

    @NotNull
    @Column(name = "seller", nullable = false)
    private UUID seller;

    @NotNull
    @OneToMany(mappedBy = "orderId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> listOrderItem = new ArrayList<>();
}
