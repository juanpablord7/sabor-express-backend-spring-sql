package com.miempresa.serviceorder.client;

import com.miempresa.serviceorder.client.fallback.UserClientFallback;
import com.miempresa.serviceorder.dto.client.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "service-user", path = "/user/me", fallback = UserClientFallback.class)
public interface UserClient {
    @GetMapping
    User getUser();
}
