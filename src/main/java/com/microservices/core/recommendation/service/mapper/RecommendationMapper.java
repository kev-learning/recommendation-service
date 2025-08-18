package com.microservices.core.recommendation.service.mapper;

import com.microservices.core.recommendation.service.dto.RecommendationDTO;
import com.microservices.core.recommendation.service.model.Recommendation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {

    @Mappings({
            @Mapping(target = "serviceAddress", source = "address")
    })
    RecommendationDTO entityToDTO(Recommendation recommendation, String address);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    Recommendation DTOToEntity(RecommendationDTO recommendationDTO);
}
