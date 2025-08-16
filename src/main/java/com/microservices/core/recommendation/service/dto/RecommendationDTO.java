package com.microservices.core.recommendation.service.dto;

import lombok.Builder;

@Builder
public record RecommendationDTO(Long recommendationId, Long productId, String author, Integer rating, String content, String serviceAddress) {
}
