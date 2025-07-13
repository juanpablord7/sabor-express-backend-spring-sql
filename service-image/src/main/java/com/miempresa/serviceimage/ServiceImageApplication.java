package com.miempresa.servicecategory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.miempresa.servicecategory.client")
@SpringBootApplication
@EnableAutoConfiguration
public class ServiceCategoryApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                ServiceCategoryApplication.class, args);
    }
}
