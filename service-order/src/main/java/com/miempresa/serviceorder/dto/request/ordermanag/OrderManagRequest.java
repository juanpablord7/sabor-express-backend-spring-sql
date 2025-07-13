package com.miempresa.serviceorder.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderManagRequest {
    private Long userId;

    private List<Long> product;

    private List<Long> quantity;
}
