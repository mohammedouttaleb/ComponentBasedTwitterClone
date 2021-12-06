package com.example.tweetservice.Mapper;

import com.example.tweetservice.Dto.TweetServiceDto;
import com.example.tweetservice.Model.TweetService;

public class TweetServiceMapper {
    public static TweetServiceDto map(TweetService tweetService){
        return new TweetServiceDto(
                tweetService.getCreatorId(),
                tweetService.getContent(),
                tweetService.getReactions(),
                tweetService.getHashtags(),
                tweetService.getComments(),
                tweetService.getCreationDate(),
                tweetService.getReports(),
                tweetService.getVisibility()
        );
    }
}
