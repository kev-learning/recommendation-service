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
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public RecommendationDTO createRecommendation(RecommendationDTO recommendationDTO) {
        Recommendation recommendation = recommendationMapper.DTOToEntity(recommendationDTO);
        recommendation = recommendationRepository.save(recommendation);

        log.debug("Created new recommendation: {}", recommendation);

        return recommendationMapper.entityToDTO(recommendation);
    }

    @Override
    public List<RecommendationDTO> findRecommendations(Long productId) {

        if(Objects.isNull(productId) || productId < 1) {
            throw new InvalidInputException("Invalid Product ID: " + productId);
        }

        List<Recommendation> recommendations = recommendationRepository.findByProductId(productId);

        log.debug("Found recommendations for product ID: {}", recommendations);

        return Optional.ofNullable(recommendations).orElse(Collections.emptyList())
                .stream().map(recommendationMapper::entityToDTO).toList();
    }

    @Override
    public void deleteRecommendations(Long productId) {

        if(Objects.isNull(productId) || productId < 1) {
            throw new InvalidInputException("Invalid Product ID: " + productId);
        }

        log.debug("Deleting recommendations for product ID: {}", productId);

        List<Recommendation> recommendations = recommendationRepository.findByProductId(productId);

        if(!CollectionUtils.isEmpty(recommendations)) {
            recommendationRepository.deleteAll(recommendations);
        }
    }
}
