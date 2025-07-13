package com.miempresa.serviceproduct.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ProductRequest {
    @NotBlank(message = "The 'name' must not be null")
    @Size(min = 2, message = "The 'name' must contain at least 2 letters")
    String name;

    @NotNull(message = "Don't provided price")
    @PositiveOrZero(message = "Price must be a positive number")
    private Double price;

    @PositiveOrZero(message = "Category_id must be a positive number")
    private Long category;

    @NotBlank(message = "Don't provided image")
    @Size(min = 4, message = "The image must contain at least 4 letters")
    private String image;

}
