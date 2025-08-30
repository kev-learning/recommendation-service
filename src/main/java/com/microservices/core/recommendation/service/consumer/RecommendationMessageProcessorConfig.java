package com.microservices.core.recommendation.service.consumer;

import com.microservices.core.recommendation.service.dto.RecommendationDTO;
import com.microservices.core.recommendation.service.service.RecommendationService;
import com.microservices.core.util.api.event.Event;
import com.microservices.core.util.exceptions.EventProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class RecommendationMessageProcessorConfig {

    @Autowired
    private RecommendationService recommendationService;

    @Bean
    public Consumer<Event<Long, RecommendationDTO>> consumeEvent() {
        return event -> {
            switch (event.getEventType()) {
                case CREATE -> {
                    RecommendationDTO recommendationDTO = event.getData();
                    log.debug("Creating product: {}", recommendationDTO);
                    recommendationService.createRecommendation(recommendationDTO).block();

                }
                case DELETE -> {
                    Long productId = event.getKey();
                    log.debug("Delete product using ID: {}", productId);
                    recommendationService.deleteRecommendations(productId).block();
                }
                default -> {
                    log.warn("Event type not supported: {}", event.getEventType());
                    throw new EventProcessingException("Event type not supported: " + event.getEventType());
                }
            }
        };
    }
}
