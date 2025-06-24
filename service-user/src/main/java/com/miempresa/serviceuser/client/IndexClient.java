package com.miempresa.serviceuser.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-index", path = "/index")
public interface IndexClient {

    @PostMapping("/user")
    Long getIndex();
}
