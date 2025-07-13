package com.miempresa.serviceproduct.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPatchRequest {
    @Size(min = 2, message = "The 'name' must contain at least 2 letters")
    private String name;

    @PositiveOrZero(message = "Price must be a positive number")
    private Double price;

    @PositiveOrZero(message = "Category_id must be a positive number")
    private Long category;

    @Size(min = 4, message = "The image must contain at least 4 letters")
    private String image;
}
