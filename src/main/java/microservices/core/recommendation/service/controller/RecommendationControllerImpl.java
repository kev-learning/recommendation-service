package microservices.core.recommendation.service.controller;

import lombok.extern.slf4j.Slf4j;
import microservices.core.recommendation.service.dto.RecommendationDTO;
import com.microservices.core.util.exceptions.InvalidInputException;
import com.microservices.core.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@RestController
public class RecommendationControllerImpl implements RecommendationController{

    @Autowired
    private ServiceUtil serviceUtil;

    @Override
    public ResponseEntity<Object> getRecommendations(Long productId) {

        if(productId < 1) {
            throw new InvalidInputException("Invalid product ID: %s".formatted(productId));
        }

        if(productId == 999) {
            log.debug("No recommendation found for product ID: {}", productId);
            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(
                Arrays.asList(
                new RecommendationDTO(1L, productId, "Author 1", 5, "Content 1", serviceUtil.getAddress()),
                new RecommendationDTO(2L, productId, "Author 2", 4, "Content 2", serviceUtil.getAddress()),
                new RecommendationDTO(3L, productId, "Author 3", 4, "Content 3", serviceUtil.getAddress())
        ));
    }
}
