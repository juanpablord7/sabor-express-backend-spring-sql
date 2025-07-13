package com.miempresa.serviceuser.dto.management;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserManagementRequest {
    private String password;

    private Long role;
}
