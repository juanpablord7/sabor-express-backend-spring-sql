package com.miempresa.serviceuser.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name can't be empty")
    @Size(min = 2, message = "The name must have at least 2 letters")
    @Column(nullable = false, unique = true)
    private String name;

    //Check if is the default role
    private Boolean isDefault;

    // Product (C U D)
    private Boolean manageProduct;

    // Order (R U D)
    private Boolean manageOrder;

    // User (R U D)
    private Boolean manageUser;

    // Role (C R U D)
    private Boolean manageRole;

    // Promote roles
    private Boolean promoteAll;

    // Edit Password
    private Boolean editPassword;

}
