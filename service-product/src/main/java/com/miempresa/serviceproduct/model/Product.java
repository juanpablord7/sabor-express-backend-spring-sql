package com.miempresa.serviceproduct.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name can't be empty")
    @Size(min = 2, message = "The name must have at least 2 letters")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "The price can't be null")
    @PositiveOrZero(message = "The price must be positive")
    @Column(nullable = false)
    private Double price;

    @NotBlank(message = "The image can't be empty")
    @Size(min = 4, message = "The name must have at least 4 letters")
    @Column(nullable = false)
    private String image;

    @NotNull(message = "The category id can't be empty")
    @PositiveOrZero(message = "The category id must be positive")
    @Column(nullable = false)
    private Long category;
}
