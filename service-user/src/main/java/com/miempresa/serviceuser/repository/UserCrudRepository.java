package com.miempresa.serviceuser.repository;

import com.miempresa.serviceuser.enums.RoleEnum;
import com.miempresa.serviceuser.model.UserModel;
import com.mongodb.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserCrudRepository extends MongoRepository<UserModel, Long> {

    //Método para encontrar todos los objetos por páginas
    @NonNull
    Page<UserModel> findAll(@NonNull Pageable pageable);

    //Método para encontrar por caegoria específica
    Page<UserModel> findAllByRole(@NonNull Pageable pageable, RoleEnum role);

    Optional<UserModel> findByUsername(String username);

}
