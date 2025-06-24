package com.miempresa.serviceorder.client;

import com.miempresa.serviceorder.client.fallback.IndexClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-index", path="/index", fallback = IndexClientFallback.class)
public interface IndexClient {

    @PostMapping("/order")
    Long getIndex();

}
