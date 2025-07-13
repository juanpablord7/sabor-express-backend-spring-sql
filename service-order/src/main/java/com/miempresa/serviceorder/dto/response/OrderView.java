package com.miempresa.serviceorder.dto.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.miempresa.serviceorder.model.OrderItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;

    private Long state;

    private Long userId;

    private Double totalPrice;

    private Date createdAt;

    private Date updatedAt;
}
