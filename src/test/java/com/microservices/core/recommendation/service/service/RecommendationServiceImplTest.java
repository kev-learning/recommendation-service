package com.microservices.core.recommendation.service.service;

import com.microservices.core.recommendation.service.dto.RecommendationDTO;
import com.microservices.core.recommendation.service.mapper.RecommendationMapper;
import com.microservices.core.recommendation.service.model.Recommendation;
import com.microservices.core.recommendation.service.repository.RecommendationRepository;
import com.microservices.core.util.exceptions.InvalidInputException;
import com.microservices.core.util.http.ServiceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceImplTest {

    private static final Long COMMON_ID = 1L;

    @Mock
    private ServiceUtil serviceUtil;

    @Mock
    private RecommendationMapper recommendationMapper;

    @Mock
    private RecommendationRepository recommendationRepository;

    @Spy
    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRecommendationTest() {
        Mockito.when(recommendationMapper.DTOToEntity(Mockito.any(RecommendationDTO.class))).thenReturn(buildRecommendation().block());
        Mockito.when(recommendationRepository.save(Mockito.any(Recommendation.class))).thenReturn(buildRecommendation());

        Mono<RecommendationDTO> recommendationDTO = recommendationService.createRecommendation(buildRecommendationDTO());

        assertNotNull(recommendationDTO);
    }

    @Test
    void getRecommendationsTest() {
        Mockito.when(recommendationRepository.findByProductId(Mockito.anyLong())).thenReturn(Flux.just(buildRecommendation()).cast(Recommendation.class));

        Flux<RecommendationDTO> recommendationDTOS = recommendationService.findRecommendations(COMMON_ID);

        assertNotNull(recommendationDTOS);
        recommendationDTOS.count().subscribe(count -> assertEquals(1, count));
    }

    @Test
    void getRecommendationsInvalidInputTest() {
        assertThrows(InvalidInputException.class, () -> recommendationService.findRecommendations(0L));
        assertThrows(InvalidInputException.class, () -> recommendationService.findRecommendations(null));
    }

    @Test
    void getRecommendationsEmptyTest() {
        Mockito.when(recommendationRepository.findByProductId(Mockito.anyLong())).thenReturn(Flux.empty());

        Flux<RecommendationDTO> recommendationDTOS = recommendationService.findRecommendations(COMMON_ID);

        assertNotNull(recommendationDTOS);
        recommendationDTOS.count().subscribe(count -> assertEquals(0, count));

        Mockito.verify(recommendationMapper, Mockito.never()).entityToDTO(Mockito.any(Recommendation.class), Mockito.anyString());
    }

    @Test
    void deleteRecommendationsTest() {
        Mockito.when(recommendationRepository.findByProductId(Mockito.anyLong())).thenReturn(Flux.just(buildRecommendation()).cast(Recommendation.class));

        recommendationService.deleteRecommendations(COMMON_ID);
    }

    @Test
    void deleteRecommendationsNotFoundTest() {
        Mockito.when(recommendationRepository.findByProductId(Mockito.anyLong())).thenReturn(Flux.empty());

        recommendationService.deleteRecommendations(COMMON_ID);

        Mockito.verify(recommendationRepository, Mockito.never()).deleteAll(Mockito.anyList());
    }

    @Test
    void deleteRecommendationsInvalidInputTest() {
        assertThrows(InvalidInputException.class, () -> recommendationService.deleteRecommendations(0L));
        assertThrows(InvalidInputException.class, () -> recommendationService.deleteRecommendations(null));
    }

    private Mono<Recommendation> buildRecommendation() {
        Recommendation recommendation = new Recommendation();
        recommendation.setRecommendationId(COMMON_ID);
        recommendation.setProductId(COMMON_ID);
        recommendation.setRating(COMMON_ID.intValue());
        recommendation.setVersion(COMMON_ID.intValue());
        recommendation.setId("ID");
        recommendation.setAuthor("AUTHOR");
        recommendation.setContent("CONTENT");

        return Mono.just(recommendation);
    }

    private RecommendationDTO buildRecommendationDTO() {
        return RecommendationDTO.builder()
                .recommendationId(COMMON_ID)
                .productId(COMMON_ID)
                .rating(COMMON_ID.intValue())
                .author("AUTHOR")
                .content("CONTENT")
                .serviceAddress("ADDRESS")
                .build();
    }
}