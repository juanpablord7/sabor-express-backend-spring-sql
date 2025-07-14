package com.miempresa.serviceorder.client.fallback;

import com.miempresa.serviceorder.client.ProductClient;
import com.miempresa.serviceorder.dto.client.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductClientFallback implements ProductClient {
    @Override
    public List<Product> findProducts(@RequestParam String productIds){
        return new ArrayList<Product>();
    }

}
