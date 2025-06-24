package com.miempresa.serviceproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.miempresa.serviceproduct.client")
@SpringBootApplication
public class ServiceProductApplication {

	public static void main(String[] args) {

		SpringApplication.run(
				ServiceProductApplication.class, args);
	}

}
