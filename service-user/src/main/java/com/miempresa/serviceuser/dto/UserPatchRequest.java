package com.miempresa.serviceuser.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPatchRequest {
    @Size(min = 3, message = "The username must have at least 3 letters")
    private String username;

    @Size(min = 3, message = "The name must have at least 3 letters")
    private String name;

    @Size(min = 3, message = "The password hash must have at least 8 letters")
    private String password;
}
