package com.miempresa.serviceuser.repository;

import com.miempresa.serviceuser.model.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //Método para encontrar todos los objetos por páginas
    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    //Método para encontrar por caegoria específica
    Page<User> findAllByRole(@NonNull Pageable pageable, Long role);

    Optional<User> findByUsername(String username);

    Optional<User> findByFullname(String fullname);

    Optional<User> findByEmail(String email);

    void deleteByUsername(String username);
}
