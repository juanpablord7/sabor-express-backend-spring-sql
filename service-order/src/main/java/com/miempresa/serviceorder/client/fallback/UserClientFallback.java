package com.miempresa.serviceorder.client.fallback;

import com.miempresa.serviceorder.client.UserClient;
import com.miempresa.serviceorder.dto.client.User;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {
    @Override
    public User getUser(){
        return User
                .builder()
                .id(null)
                .build();
    }
}