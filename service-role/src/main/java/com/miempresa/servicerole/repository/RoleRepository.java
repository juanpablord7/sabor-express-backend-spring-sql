package com.miempresa.serviceuser.repository;

import com.miempresa.serviceuser.model.Role;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    Optional<Role> findByIsDefault(Boolean isDefault);

    void deleteByName(String name);

}
