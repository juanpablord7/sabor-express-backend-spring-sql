package com.miempresa.serviceuser.dto;

import com.miempresa.serviceuser.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    private String password;
}
