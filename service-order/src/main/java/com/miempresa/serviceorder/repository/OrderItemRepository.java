package com.miempresa.serviceorder.repository;

import com.miempresa.serviceorder.model.OrderItem;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @NonNull
    Page<OrderItem> findAll(@NonNull Pageable pageable);

    @NonNull
    Page<OrderItem> findByProductIdAndOrder_Id(@NonNull Long productId, @NonNull Long orderId, @NonNull Pageable pageable);

    @NonNull
    Page<OrderItem> findByProductId(@NonNull Long productId, @NonNull Pageable pageable);

    @NonNull
    Page<OrderItem> findByOrder_Id(@NonNull Long orderId, @NonNull Pageable pageable);

}
