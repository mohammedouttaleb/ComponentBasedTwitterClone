package com.example.recommendationservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Service
public class RecommendationService {

    public Set<Object> getRecommendations(String token) {
        final String uri = "http://localhost:8080/api/auth/token=" + token;
        RestTemplate restTemplate = new RestTemplate();
        Long userId = restTemplate.getForObject(uri, Long.class);

        final String recommendationsUri = "http://192.168.43.20:8082/recommendations/" + userId;
        RestTemplate recommendationRestTemplate = new RestTemplate();
        Set<Object> recommendations = restTemplate.getForObject(recommendationsUri, Set.class);
        return recommendations;
    }
}
