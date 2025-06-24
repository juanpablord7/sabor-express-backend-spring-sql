package com.miempresa.serviceorder.client.fallback;

import com.miempresa.serviceorder.client.IndexClient;
import org.springframework.stereotype.Component;

@Component
public class IndexClientFallback implements IndexClient {
    @Override
    public Long getIndex(){
        throw new RuntimeException("The index microservice don't provide a index");
    }
}
