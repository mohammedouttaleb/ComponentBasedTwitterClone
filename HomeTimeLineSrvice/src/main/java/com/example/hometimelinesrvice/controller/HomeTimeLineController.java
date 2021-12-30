package com.example.hometimelinesrvice.controller;

import com.example.hometimelinesrvice.service.HomeTimeLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class HomeTimeLineController {


    private HomeTimeLineService homeTimeLineService;

    @Autowired
    public HomeTimeLineController(HomeTimeLineService homeTimeLineService) {
        Assert.notNull(homeTimeLineService, "Service must not be null");
        this.homeTimeLineService = homeTimeLineService;
    }

    @GetMapping("/test")
    public String test(/*String userId*/) {
        return homeTimeLineService.getUserTweets("21");
//        return homeTimeLineService.getUserTweets("21");

    }

    @GetMapping("/homeTimeLine/{token}")
    public Set<Object> getUserHomeTimeLine(@PathVariable String token ) {
        return homeTimeLineService.getHomeTimeLine(token);
    }

}
