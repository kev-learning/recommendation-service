package com.microservices.core.recommendation.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ImportAutoConfiguration(exclude = KafkaAutoConfiguration.class)
class RecommendationServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
