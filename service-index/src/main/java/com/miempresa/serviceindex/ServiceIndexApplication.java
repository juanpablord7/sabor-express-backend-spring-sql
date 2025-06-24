package com.miempresa.serviceindex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.miempresa.serviceindex.client")
public class ServiceIndexApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                ServiceIndexApplication.class, args);
    }
}
