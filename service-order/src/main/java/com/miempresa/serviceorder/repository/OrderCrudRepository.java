package com.miempresa.serviceorder.repository;


import com.miempresa.serviceorder.model.OrderModel;
import com.mongodb.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderCrudRepository extends MongoRepository<OrderModel, Long> {

    //Método para encontrar todos los objetos por páginas
    @NonNull
    Page<OrderModel> findAll(@NonNull Pageable pageable);

    //Método para encontrar todos los objetos por usuario
    @NonNull
    Page<OrderModel> findByUserId(Long userId);

    //Método para encontrar todos los objetos por state
    @NonNull
    Page<OrderModel> findByState(Long state);
}
