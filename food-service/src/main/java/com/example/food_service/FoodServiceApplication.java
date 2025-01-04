package com.example.food_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.food_service.repository")
@EnableMongoAuditing
public class FoodServiceApplication {

	public static void main(String[] args) {

//		SpringApplication.run(FoodServiceApplication.class, args);
		SpringApplication app = new SpringApplication(FoodServiceApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8082"));
		// Fahim to do idk why i need to do this.yml is not working
		app.run(args);

	}

}
