package com.microservices.core.recommendation.service.mapper;

import com.microservices.core.recommendation.service.dto.RecommendationDTO;
import com.microservices.core.recommendation.service.model.Recommendation;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationMapperTest {

    private static final Long COMMON_ID = 1L;

    RecommendationMapper recommendationMapper = Mappers.getMapper(RecommendationMapper.class);

    @Test
    void mapEntityToDTOTest() {
        Recommendation recommendation = new Recommendation();
        recommendation.setRecommendationId(COMMON_ID);
        recommendation.setProductId(COMMON_ID);
        recommendation.setRating(COMMON_ID.intValue());
        recommendation.setVersion(COMMON_ID.intValue());
        recommendation.setId("ID");
        recommendation.setAuthor("AUTHOR");
        recommendation.setContent("CONTENT");

        RecommendationDTO recommendationDTO = recommendationMapper.entityToDTO(recommendation, "Address");

        assertEquals(recommendation.getRecommendationId(), recommendationDTO.recommendationId());
        assertEquals(recommendation.getProductId(), recommendationDTO.productId());
        assertEquals(recommendation.getAuthor(), recommendationDTO.author());
        assertEquals(recommendation.getContent(), recommendationDTO.content());
        assertEquals(recommendation.getRating(), recommendationDTO.rating());
        assertEquals("Address", recommendationDTO.serviceAddress());
    }

    @Test
    void mapDTOToEntityTest() {
        RecommendationDTO recommendationDTO = RecommendationDTO.builder()
                .recommendationId(COMMON_ID)
                .productId(COMMON_ID)
                .rating(COMMON_ID.intValue())
                .author("AUTHOR")
                .content("CONTENT")
                .serviceAddress("ADDRESS")
                .build();

        Recommendation recommendation = recommendationMapper.DTOToEntity(recommendationDTO);

        assertEquals(recommendationDTO.recommendationId(), recommendation.getRecommendationId());
        assertEquals(recommendationDTO.productId(), recommendation.getProductId());
        assertEquals(recommendationDTO.rating(), recommendation.getRating());
        assertEquals(recommendationDTO.author(), recommendation.getAuthor());
        assertEquals(recommendationDTO.content(), recommendation.getContent());
    }

}