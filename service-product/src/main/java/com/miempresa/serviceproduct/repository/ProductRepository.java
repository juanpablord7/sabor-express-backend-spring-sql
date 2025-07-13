package com.miempresa.serviceproduct.repository;

import com.mongodb.lang.NonNull;
import com.miempresa.serviceproduct.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductCrudRepository extends MongoRepository<Product, Long>{

    //Método para encontrar todos los objetos por páginas
    @NonNull
    Page<Product> findAll(@NonNull Pageable pageable);

    //Método para encontrar por caegoria específica
    Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);

    //Método para encontrar productos por una lista de products_id
    List<Product> findAllByIdIn(List<Long> ids);

}