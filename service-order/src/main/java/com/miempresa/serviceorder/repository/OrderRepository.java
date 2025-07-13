package com.miempresa.serviceorder.repository;


import com.miempresa.serviceorder.model.Order;
import com.mongodb.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderCrudRepository extends MongoRepository<Order, Long> {

    //Método para encontrar todos los objetos por páginas
    @NonNull
    Page<Order> findAll(@NonNull Pageable pageable);

    //Método para encontrar todos los objetos por usuario
    @NonNull
    Page<Order> findByUserId(Long userId);

    //Método para encontrar todos los objetos por state
    @NonNull
    Page<Order> findByState(Long state);
}
