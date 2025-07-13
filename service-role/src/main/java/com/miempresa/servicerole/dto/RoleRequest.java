package com.miempresa.serviceuser.dto.role;

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
public class RoleRequest {
    @NotBlank(message = "The name can't be empty")
    @Size(min = 2, message = "The name must have at least 2 letters")
    private String name;

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
}
