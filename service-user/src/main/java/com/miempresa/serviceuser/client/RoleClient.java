package com.miempresa.serviceuser.client;

import com.miempresa.serviceuser.client.fallback.RoleClientFallback;
import com.miempresa.serviceuser.dto.client.RoleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;;

@FeignClient(name = "service-role", path = "/role", fallback = RoleClientFallback.class)
public interface RoleClient {

    @GetMapping("/getDefault")
    Long getDefaultRole();

    @GetMapping("/{id}")
    RoleResponse getRole(@PathVariable("id") Long id);;
}

