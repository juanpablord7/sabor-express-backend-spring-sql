package com.miempresa.serviceproduct.client.fallback;

import com.miempresa.serviceproduct.client.IndexClient;
import org.springframework.stereotype.Component;

@Component
public class IndexClientFallback implements IndexClient {

    @Override
    public Long getIndex() {
        throw new RuntimeException("The index microservice don't provide a index");
    }
}
