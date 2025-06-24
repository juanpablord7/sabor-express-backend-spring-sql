package com.miempresa.serviceorder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequest {
    private List<Long> order;

    private Long userId;
}
