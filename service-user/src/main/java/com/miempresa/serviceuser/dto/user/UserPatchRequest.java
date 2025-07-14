package com.miempresa.serviceuser.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPatchRequest {
    private String username;

    private String fullname;

    private String email;

    private String password;
}
