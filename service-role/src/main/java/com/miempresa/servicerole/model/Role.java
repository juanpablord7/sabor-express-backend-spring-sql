package com.miempresa.servicerole.model;

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
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name can't be empty")
    @Size(min = 2, message = "The name must have at least 2 letters")
    @Column(nullable = false, unique = true)
    private String name;

    //Check if is the default role
    private boolean isDefault;

    // Product (C U D)
    private boolean manageProduct;

    // Order (R U D)
    private boolean manageOrder;

    // User (R U D)
    private boolean manageUser;

    // Role (C R U D)
    private boolean manageRole;

    // Promote roles
    private boolean promoteAll;

    // Edit Password
    private boolean editPassword;

    // Chef
    private boolean chef;

    // Delivery
    private boolean delivery;
}
