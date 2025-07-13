package com.miempresa.serviceorder.dto.request;

import java.util.List;

public class OrderPatchRequest {
    private Long userId;

    private List<Long> product;

    private List<Long> quantity;
}
