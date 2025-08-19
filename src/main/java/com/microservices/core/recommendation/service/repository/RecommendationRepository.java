package com.microservices.core.recommendation.service.repository;

import com.microservices.core.recommendation.service.model.Recommendation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface RecommendationRepository extends ReactiveCrudRepository<Recommendation, String> {

    Flux<Recommendation> findByProductId(Long productId);
}
