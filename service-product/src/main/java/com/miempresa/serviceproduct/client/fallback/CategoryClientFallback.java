package com.miempresa.serviceproduct.client.fallback;

import com.miempresa.serviceproduct.client.CategoryClient;
import org.springframework.stereotype.Component;

@Component
public class CategoryClientFallback implements CategoryClient {

    @Override
    public String getCategory(Long categoryId){
        return null;
    }
}