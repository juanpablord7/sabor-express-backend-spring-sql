package com.miempresa.servicecategory.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.miempresa.servicecategory.utils.annotation.customAnnotation.NotXSS;
import java.util.Map;

@Data
@AllArgsConstructor
public class CategoryRequest {
    @NotBlank(message = "The 'name' must not be null")
    @Size(min = 2, message = "The 'name' must contain at least 2 letters")
    @NotXSS
    private String name;

    @NotBlank(message = "Don't provided image")
    @Size(min = 4, message = "The image must contain at least 4 letters")
    @NotXSS
    private String image;

}
