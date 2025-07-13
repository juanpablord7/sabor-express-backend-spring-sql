package com.miempresa.servicerole.repository;

import com.miempresa.servicerole.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    Optional<Role> findByIsDefault(Boolean isDefault);

    void deleteByName(String name);

}
