package com.microservices.core.recommendation.service.service;

import com.microservices.core.recommendation.service.dto.RecommendationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RecommendationService {

    Mono<RecommendationDTO> createRecommendation(RecommendationDTO recommendationDTO);

    Flux<RecommendationDTO> findRecommendations(Long productId);

    Mono<Void> deleteRecommendations(Long productId);
}
