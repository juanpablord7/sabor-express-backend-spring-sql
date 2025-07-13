package com.miempresa.servicerole.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePatchRequest {
    private String name;

    //Will use isDefault because
    @JsonProperty("default")
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

    // Chef
    private Boolean chef;

    // Delivery
    private Boolean delivery;
}
