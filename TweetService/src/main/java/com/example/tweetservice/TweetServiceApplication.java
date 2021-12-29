package com.example.tweetservice;

import com.example.tweetservice.Model.TweetService;
import com.example.tweetservice.Model.Util.Reactions;
import com.example.tweetservice.Model.Util.Visibility;
import com.example.tweetservice.Repository.TweetServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class TweetServiceApplication implements CommandLineRunner {

    @Autowired
    private TweetServiceRepository tweetServiceRepository;

    public static void main(String[] args) {
        SpringApplication.run(TweetServiceApplication.class, args);
    }

    public void run(String... strings){
        TweetService tweet = new TweetService();
        List<String> hashTags = new ArrayList<>();
        hashTags.add("JADER");
        Set<String> likes = new HashSet<>();
        Set<String> retweets = new HashSet<>();
        likes.add("1");
        likes.add("2");
        retweets.add("1");
        Reactions react = new Reactions(likes,retweets,"2");
        tweet.setReactions(react);
        tweet.setHashtags(hashTags);
        tweet.setCreationDate(new Date().toInstant());
        tweet.setCreatorId("21");
        tweet.setVisibility(Visibility.ALL);
        tweetServiceRepository.save(tweet);

    }
}
