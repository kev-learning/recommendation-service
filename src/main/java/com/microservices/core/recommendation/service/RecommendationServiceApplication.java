package com.microservices.core.recommendation.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@ComponentScan("com.microservices.core")
@EnableMongoRepositories(basePackages = {"com.microservices.core"})
public class RecommendationServiceApplication {

	public static void main(String[] args) {
		Hooks.enableAutomaticContextPropagation();
		SpringApplication.run(RecommendationServiceApplication.class, args);
	}

}
