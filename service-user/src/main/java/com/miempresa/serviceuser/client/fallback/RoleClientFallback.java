package com.miempresa.serviceuser.client.fallback;

import com.miempresa.serviceuser.client.RoleClient;
import com.miempresa.serviceuser.dto.client.RoleResponse;
import org.springframework.stereotype.Component;

@Component
public class RoleClientFallback implements RoleClient {
    @Override
    public Long getDefaultRole(){
        return null;
    }

    @Override
    public RoleResponse getRole(Long id) {
        return RoleResponse.builder().build();
    }
}
