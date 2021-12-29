package com.example.tweetservice.Model.Util;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Reactions {

    private Reaction type;
    private Set<String> likes;
    private Set<String> retweets;
    private String tweetId;

    public Reactions(Set<String> likes, Set<String> retweets,String tweetId) {
        this.likes = likes;
        this.retweets = retweets;
        this.tweetId = tweetId;
    }
}

