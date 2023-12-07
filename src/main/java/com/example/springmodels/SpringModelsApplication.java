package com.example.springmodels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.springmodels"})
public class SpringModelsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringModelsApplication.class, args);
	}

}
