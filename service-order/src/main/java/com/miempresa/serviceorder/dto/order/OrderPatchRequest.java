package com.miempresa.serviceorder.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPatchRequest {
    private List<Long> product;

    private List<Long> quantity;
}
