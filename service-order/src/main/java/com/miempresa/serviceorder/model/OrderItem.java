package com.miempresa.serviceorder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @ToString.Exclude //Avoid infinite recursion
    @JsonIgnore
    private Order order;

    @NotNull(message = "The Product id can't be null")
    @PositiveOrZero(message = "The Product id must be positive")
    @Column(name = "product_id",nullable = false)
    private Long productId;

    @NotBlank(message = "The Product name can't be null")
    @Column(name = "product_name",nullable = false)
    private String productName;

    @NotNull(message = "The Quantity can't be null")
    @Positive(message = "The Quantity must be positive")
    @Column(nullable = false)
    private Long quantity;

    @NotNull(message = "The Price can't be null")
    @Positive(message = "ThePrice must be positive")
    @Column(nullable = false)
    private Double price;

}
