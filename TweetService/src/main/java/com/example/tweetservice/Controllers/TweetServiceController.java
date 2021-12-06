package com.example.tweetservice.Controllers;

import com.example.tweetservice.Dto.TweetServiceDto;
import com.example.tweetservice.Model.TweetService;
import com.example.tweetservice.Service.TweetServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/TweetServices")
@Validated
public class TweetServiceController {

    @Autowired
    private TweetServiceService tweetService;

    @GetMapping("GetAllTweets")
    List<TweetService> loadAllTweets(){
        return tweetService.getAllTweets();
    }
}
