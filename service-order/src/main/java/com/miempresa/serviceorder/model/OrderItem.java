package com.miempresa.serviceorder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
@Entity
public class Order_item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The User id can't be null")
    @PositiveOrZero(message = "The User id must be positive")
    @Column(name = "order_id",nullable = false)
    private Long orderId;

    @NotNull(message = "The User id can't be null")
    @PositiveOrZero(message = "The User id must be positive")
    @Column(name = "product_id",nullable = false)
    private Long productId;

    @NotNull(message = "The User id can't be null")
    @Positive(message = "The User id must be positive")
    @Column(nullable = false)
    private Long quantity;

    @NotNull(message = "The User id can't be null")
    @Positive(message = "The User id must be positive")
    @Column(nullable = false)
    private Double price;

}
