package com.example.tweetservice.Model.Util;

import lombok.Data;

import java.util.List;

@Data
public class Reactions {

    private List<Long> likes;
    private List<Long> retweets;
    private Long tweetId;

    public Reactions(List<Long> likes, List<Long> retweets,Long tweetId) {
        this.likes = likes;
        this.retweets = retweets;
        this.tweetId = tweetId;
    }
}
