package com.microservices.core.recommendation.service.service;

import com.microservices.core.recommendation.service.dto.RecommendationDTO;

import java.util.List;

public interface RecommendationService {

    RecommendationDTO createRecommendation(RecommendationDTO recommendationDTO);

    List<RecommendationDTO> findRecommendations(Long productId);

    void deleteRecommendations(Long productId);
}
