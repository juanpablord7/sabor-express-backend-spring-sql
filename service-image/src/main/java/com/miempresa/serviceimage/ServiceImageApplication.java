package com.miempresa.serviceimage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.miempresa.servicecategory.client")
@SpringBootApplication
@EnableAutoConfiguration
public class ServiceImageApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                ServiceImageApplication.class, args);
    }
}
