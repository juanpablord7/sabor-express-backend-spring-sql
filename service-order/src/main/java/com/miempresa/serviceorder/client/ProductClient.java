package com.miempresa.serviceorder.client;

import com.miempresa.serviceorder.client.fallback.ProductClientFallback;
import com.miempresa.serviceorder.dto.client.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-product", path = "/product", fallback = ProductClientFallback.class)
public interface ProductClient {

    @GetMapping("/find")
    List<Product> findProducts(@RequestParam String productIds);
}
