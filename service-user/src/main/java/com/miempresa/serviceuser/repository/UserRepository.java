package com.miempresa.serviceuser.repository;

import com.miempresa.serviceuser.dto.user.UserView;
import com.miempresa.serviceuser.model.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //Método para encontrar todos los objetos por páginas
    @NonNull
    Page<UserView> findAllProjectedBy(@NonNull Pageable pageable);

    //Método para encontrar por caegoria específica
    Page<UserView> findAllByRole(@NonNull Pageable pageable, Long role);

    //To find all the user information (password included)
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    //To return the information of the user
    Optional<UserView> findProjectedById(Long id);

    Optional<UserView> findProjectedByUsername(String username);

    Optional<UserView> findProjectedByEmail(String email);


    void deleteByUsername(String username);
}
