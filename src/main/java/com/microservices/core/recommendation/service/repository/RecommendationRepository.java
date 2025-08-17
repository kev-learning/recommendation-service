package com.microservices.core.recommendation.service.repository;

import com.microservices.core.recommendation.service.model.Recommendation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecommendationRepository extends CrudRepository<Recommendation, String> {

    List<Recommendation> findByProductId(Long productId);
}
