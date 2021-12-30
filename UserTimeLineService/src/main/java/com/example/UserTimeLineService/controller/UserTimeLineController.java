package com.example.UserTimeLineService.controller;


import com.example.UserTimeLineService.dto.UserTimeLineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserTimeLineController {


    RestTemplate restTemplate=new RestTemplate();
     String tweetApiBaseUrl = "http://10.1.5.10:"+8080+"/users/";
     String recommendationApiBaseUrl = "http://localhost:"+3000+"/createRoom/";





    @GetMapping("/hello")
    public ResponseEntity<String> hello()  {
        return ResponseEntity.ok().body("Hello World!!!!!!");
    }
    @GetMapping("/getUserTimeLineContent/{id}")
    public ResponseEntity<List<String>> getAllPosts(@PathVariable("id") String userID) throws URISyntaxException {

        List<String> tweetsAndAdsList=new ArrayList<>();
        String tweets=getTweetsFromTweetApi(userID);
        tweetsAndAdsList.add(tweets);
        // String ads=getRecommendationsFromRecommendationsApi(userID);
        // tweetsAndAdsList.add(ads);
        return ResponseEntity.ok().body(tweetsAndAdsList);
    }

    private String  getTweetsFromTweetApi(String userID) throws URISyntaxException {
        tweetApiBaseUrl=tweetApiBaseUrl.concat(userID);
        URI uri = new URI(tweetApiBaseUrl);
        String results = restTemplate.getForObject(uri, String.class);
        System.out.println(results);
        return  results;
    }
    private String  getRecommendationsFromRecommendationsApi(String userID) throws URISyntaxException {
        recommendationApiBaseUrl=recommendationApiBaseUrl.concat(userID);
        URI uri = new URI(recommendationApiBaseUrl);
        String results = restTemplate.getForObject(uri, String.class);
        System.out.println(results);
        return  results;
    }

//    @Bean
//    public RestTemplate getRestTemplate(){
//        return  new RestTemplate();
//    }
}
