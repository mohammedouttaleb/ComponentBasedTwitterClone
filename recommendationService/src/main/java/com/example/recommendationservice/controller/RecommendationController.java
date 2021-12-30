package com.example.recommendationservice.controller;

import com.example.recommendationservice.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class RecommendationController {

    RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        Assert.notNull(recommendationService, "recommendation service must not be null");
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendations/{token}")
    public Set<Object> getRecommendations(@PathVariable String token) {
        return recommendationService.getRecommendations(token);
    }

}
