package com.miempresa.serviceproduct.client;

import com.miempresa.serviceproduct.client.fallback.IndexClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-index", path = "/index", fallback = IndexClientFallback.class)
public interface IndexClient {
    @PostMapping("/product")
    Long getIndex();
}
