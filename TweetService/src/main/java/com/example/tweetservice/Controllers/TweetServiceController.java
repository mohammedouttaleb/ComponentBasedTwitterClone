package com.example.tweetservice.Controllers;

import com.example.tweetservice.Model.TweetService;
import com.example.tweetservice.Model.Util.Comments;
import com.example.tweetservice.Model.Util.Multimedia;
import com.example.tweetservice.Model.Util.Reaction;
import com.example.tweetservice.Model.Util.Visibility;
import com.example.tweetservice.Service.TweetServiceService;
import com.mongodb.event.CommandEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController(value = "/TweetServices")
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
public class TweetServiceController {

    @Autowired
    private TweetServiceService tweetServiceService;

    @GetMapping("/getAllTweets")
    public List<TweetService> loadAllTweets(){
        return tweetServiceService.getAllTweets();
    }

    @GetMapping("/{id}")
    public Optional<TweetService> getTweetByeId(@PathVariable("id") String tweetId){
        return tweetServiceService.getTweetById(tweetId);
    }

    @GetMapping("/users/{id}")
    public Optional<List<TweetService>> getTweetByeUserId(@PathVariable("id") String userId){
        return tweetServiceService.getAllTweetsByUser(userId);
    }

    @PostMapping("/createTweet")
    public void createTweet(@RequestBody TweetService tweetService){
        tweetServiceService.createTweet(tweetService);
    }

    @PutMapping("/{id}/{Reaction}/{userId}")
    public void addReaction(@PathVariable("id") String tweetId, @PathVariable("Reaction") Reaction reactionType, @PathVariable("userId") String userId ) throws Exception {
        tweetServiceService.addReaction(tweetId, reactionType, userId);
    }

    @PutMapping("/updateTweet")
    public void updateTweet(@RequestBody TweetService tweetService) throws Exception {
        tweetServiceService.updateTweet(tweetService);
    }

    @PutMapping("/{comment}")
    public void addComments(@PathVariable("comment") Comments comment) throws Exception {
        tweetServiceService.addComment(comment);
    }

    @RequestMapping("/{id}/tweetVisibility/{Visibility}")
    public void updateVisibility(@PathVariable("id") String tweetId,@PathVariable("Visibility") Visibility visibility) throws Exception {
        ResponseEntity<Visibility> responseEntity =
                new RestTemplate().getForEntity(
                        "http://localhost:8080/{id}/tweetVisibility/{Visibility}", Visibility.class);
        Visibility visibility1 = responseEntity.getBody();
        tweetServiceService.updateVisibility(tweetId,visibility1);
    }

}
