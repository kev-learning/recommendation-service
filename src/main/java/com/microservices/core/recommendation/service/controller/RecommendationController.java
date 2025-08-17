package com.microservices.core.recommendation.service.controller;

import com.microservices.core.recommendation.service.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import com.microservices.core.recommendation.service.dto.RecommendationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping(value = "/recommendation", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<RecommendationDTO>> getRecommendations(@RequestParam(value = "productId", required = true) Long productId) {
        return ResponseEntity.ok(recommendationService.findRecommendations(productId));
    }

    @PostMapping(value = "/recommendation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<RecommendationDTO>> createRecommendations(@RequestBody List<RecommendationDTO> recommendationDTOS){
        List<RecommendationDTO> recommendationDTOList = Optional.ofNullable(recommendationDTOS).orElse(Collections.emptyList())
                .stream().map(recommendationService::createRecommendation).toList();

        return new ResponseEntity<>(recommendationDTOList, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/recommendation")
    void deleteRecommendations(@RequestParam(value = "productId", required = true) Long productId) {
        recommendationService.deleteRecommendations(productId);
    }
}
