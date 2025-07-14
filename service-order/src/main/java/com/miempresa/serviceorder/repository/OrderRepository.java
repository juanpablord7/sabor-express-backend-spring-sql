package com.miempresa.serviceorder.repository;


import com.miempresa.serviceorder.dto.order.OrderView;
import com.miempresa.serviceorder.model.Order;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    //Método para encontrar todos los objetos por páginas
    @NonNull
    Page<OrderView> findAllProjectedBy(@NonNull  Pageable pageable);

    //Método para encontrar todos los objetos por usuario
    Page<OrderView> findByUserId(Long userId, Pageable pageable);

    //Método para encontrar todos los objetos por state
    Page<OrderView> findByState(Long state, Pageable pageable);

    //Método para encontrar todos los objetos por usuario
    Page<OrderView> findByUserIdAndState(Long userId, Long state, Pageable pageable);

    //Método para encontrar una orden de un usuario
    Optional<Order> findByIdAndUserId(Long Id, Long userId);
}
