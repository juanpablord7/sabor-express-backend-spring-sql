package com.miempresa.serviceproduct.client;

import com.miempresa.serviceproduct.client.fallback.CategoryClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-category", path = "/category", fallback = CategoryClientFallback.class)
public interface CategoryClient {
    @GetMapping("/{categoryId}")
    String getCategory(@PathVariable("categoryId") Long categoryId);
}
