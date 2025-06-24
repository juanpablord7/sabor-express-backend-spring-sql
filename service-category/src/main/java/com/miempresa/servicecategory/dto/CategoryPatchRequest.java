package com.miempresa.servicecategory.dto;

import com.miempresa.servicecategory.utils.annotation.customAnnotation.NotXSS;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPatchRequest {
    @Size(min = 2, message = "The 'name' must contain at least 2 letters")
    private String name;

    @Size(min = 4, message = "The image must contain at least 4 letters")
    @NotXSS
    private String image;
}
