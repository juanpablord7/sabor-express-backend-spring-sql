package com.miempresa.serviceorder.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonManagedReference
    private List<OrderItem> items;

    @NotNull(message = "The State can't be null")
    @Positive(message = "The State must be positive")
    @Column(nullable = false)
    private Long state;

    @NotNull(message = "The User id can't be null")
    @PositiveOrZero(message = "The User id must be positive")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull(message = "The User id can't be null")
    @PositiveOrZero(message = "The User id must be positive")
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
}
