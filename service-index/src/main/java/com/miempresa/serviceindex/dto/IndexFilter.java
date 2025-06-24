package com.miempresa.serviceindex.dto;

import com.miempresa.serviceindex.utils.annotation.customAnnotation.NotXSS;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexFilter {
    @NotXSS
    private String nameIndex;

    @PositiveOrZero
    private Long sequenceValue;

}
