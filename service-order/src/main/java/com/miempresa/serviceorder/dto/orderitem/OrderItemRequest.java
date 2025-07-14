package com.miempresa.serviceorder.dto.orderitem;

import com.miempresa.serviceorder.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    private Order order;

    private Long productId;

    private String productName;

    private Long quantity;

    private Double price;

}
