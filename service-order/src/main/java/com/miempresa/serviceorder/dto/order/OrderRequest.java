package com.miempresa.serviceorder.dto.order;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequest {
    @NotNull(message = "The product list must not be null")
    @NotEmpty(message = "The product list must not be empty")
    @Size(min = 1, message = "The product list must contain at least one item")
    private List<
            @NotNull(message = "Product ID cannot be null")
            @PositiveOrZero(message = "Product ID must be positive")
                    Long> product;

    @NotNull(message = "The quantity list must not be null")
    @NotEmpty(message = "The quantity list must not be empty")
    @Size(min = 1, message = "The quantity list must contain at least one item")
    private List<
            @NotNull(message = "Quantity cannot be null")
            @Positive(message = "Quantity must be greater than zero")
                    Long> quantity;

}
