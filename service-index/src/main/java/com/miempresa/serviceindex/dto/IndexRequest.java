package com.miempresa.serviceindex.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexRequest {
    @NotBlank
    private String nameIndex;

    @PositiveOrZero
    private Long sequenceValue;
}
