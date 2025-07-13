package com.miempresa.serviceproduct.repository;

import com.miempresa.serviceproduct.model.Product;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //Método para encontrar todos los objetos por páginas
    @NonNull
    Page<Product> findAll(@NonNull Pageable pageable);

    //Método para encontrar por caegoria específica
    Page<Product> findAllByCategory(Long category, Pageable pageable);

    //Método para encontrar productos por una lista de products_id
    List<Product> findAllByIdIn(List<Long> ids);

}