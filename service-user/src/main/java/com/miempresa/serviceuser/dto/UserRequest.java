package com.miempresa.serviceuser.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String fullname;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
