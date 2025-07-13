package com.miempresa.serviceuser.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {

    @NotNull
    private Long id;

    @NotBlank(message = "The name can't be empty")
    private String name;

    //Check if is the default role
    @JsonProperty("default")
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