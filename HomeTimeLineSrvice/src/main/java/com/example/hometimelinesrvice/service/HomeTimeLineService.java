package com.example.hometimelinesrvice.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HomeTimeLineService {

    public String getUserTweets(String userId) {
        final String uri = "http://localhost:8080/users/" + userId;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        System.out.println(result);
        return result;
    }

    public Set<Object> getHomeTimeLine(String token) {
        //TODO restTemplate

        RestTemplate restTemplate = new RestTemplate();
        final String userUri = "http://192.168.43.96:8080/api/auth/token=" + token;
        String userId = restTemplate.getForObject(userUri, String.class);
        final String followsUri = "http://localhost:8082/followsIds/" + userId;
        Set<Integer> followsString = restTemplate.getForObject(followsUri, Set.class);
        Set<Object> follows = new HashSet<>();
//        follows.addAll(followsString);
        for (Integer follow: followsString) {
            String tweetsUri = "http://localhost:8080/users/" + follow;
            follows.add(restTemplate.getForObject(tweetsUri, ArrayList.class));
        }
        return follows;
    }



}
