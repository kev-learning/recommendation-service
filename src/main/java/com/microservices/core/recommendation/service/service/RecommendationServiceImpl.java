package com.microservices.core.recommendation.service.service;

import com.microservices.core.recommendation.service.dto.RecommendationDTO;
import com.microservices.core.recommendation.service.mapper.RecommendationMapper;
import com.microservices.core.recommendation.service.model.Recommendation;
import com.microservices.core.recommendation.service.repository.RecommendationRepository;
import com.microservices.core.util.exceptions.InvalidInputException;
import com.microservices.core.util.http.ServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.logging.Level;

@Slf4j
@Service
public class RecommendationServiceImpl implements RecommendationService{

    @Autowired
    private ServiceUtil serviceUtil;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private RecommendationMapper recommendationMapper;

    @Override
    public Mono<RecommendationDTO> createRecommendation(RecommendationDTO recommendationDTO) {
        Recommendation recommendation = recommendationMapper.DTOToEntity(recommendationDTO);
        Mono<RecommendationDTO> createdRecommendation = recommendationRepository.save(recommendation)
                .log(log.getName(), Level.FINE)
                .map(entity -> recommendationMapper.entityToDTO(entity, serviceUtil.getAddress()));

        log.debug("Created new recommendation: {}", createdRecommendation);

        return createdRecommendation;
    }

    @Override
    public Flux<RecommendationDTO> findRecommendations(Long productId) {

        if(Objects.isNull(productId) || productId < 1) {
            throw new InvalidInputException("Invalid Product ID: " + productId);
        }

        Flux<RecommendationDTO> recommendations = recommendationRepository.findByProductId(productId)
                .log(log.getName(), Level.FINE)
                .map(entity -> recommendationMapper.entityToDTO(entity, serviceUtil.getAddress()));

        log.debug("Found recommendations for product ID: {}", recommendations);

        return recommendations;
    }

    @Override
    public Mono<Void> deleteRecommendations(Long productId) {

        if(Objects.isNull(productId) || productId < 1) {
            throw new InvalidInputException("Invalid Product ID: " + productId);
        }

        log.debug("Deleting recommendations for product ID: {}", productId);

        return recommendationRepository.findByProductId(productId)
                .map(recommendationRepository::delete).flatMap(e -> e).then();
    }
}
