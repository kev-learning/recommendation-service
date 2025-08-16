package microservices.core.recommendation.service.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface RecommendationController {

    @GetMapping(value = "/recommendation", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getRecommendations(@RequestParam(value = "productId", required = true) Long productId);
}
