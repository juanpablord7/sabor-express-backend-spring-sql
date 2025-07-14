package com.miempresa.serviceuser.client.fallback;

import com.miempresa.serviceuser.client.RoleClient;
import com.miempresa.serviceuser.dto.client.RoleResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class RoleClientFallback implements RoleClient {

    @Override
    public Long getDefaultRole() {
        return null;
    }

    ;

    @Override
    public RoleResponse getRole(@PathVariable("id") Long id) {
        return RoleResponse
                .builder()
                .build();
    }

    ;
}
