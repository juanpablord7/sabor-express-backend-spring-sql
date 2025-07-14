package com.miempresa.serviceorder.client.fallback;

import com.miempresa.serviceorder.client.UserClient;
import com.miempresa.serviceorder.dto.client.User;
import org.springframework.cloud.openfeign.FallbackFactory;

public class UserClientFallback implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable cause){
        return new UserClient() {
            @Override
            public User getUser(){
                return User
                        .builder()
                        .id(null)
                        .build();
            }
        };
    }
}