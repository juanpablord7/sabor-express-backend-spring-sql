package com.miempresa.serviceuser.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Instant expiresAt;
}
