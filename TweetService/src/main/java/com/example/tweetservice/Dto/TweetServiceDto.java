package com.example.tweetservice.Dto;

import com.example.tweetservice.Model.Util.Comments;
import com.example.tweetservice.Model.Util.MultiMedia;
import com.example.tweetservice.Model.Util.Reactions;
import com.example.tweetservice.Model.Util.Visibility;

import java.time.Instant;
import java.util.List;

public class TweetServiceDto {

    private String creatorId;
    private List<MultiMedia> content;
    private Reactions reactions;
    private List<String> hashtags;
    private List<Comments> comments;
    private Instant creationDate;
    private List<Long> reports;
    private Visibility visibility;

    public TweetServiceDto(String creatorId,List<MultiMedia> content, Reactions reactions, List<String> hashtags, List<Comments> comments, Instant creationDate, List<Long> reports, Visibility visibility) {
        this.creatorId =creatorId;
        this.content = content;
        this.reactions = reactions;
        this.hashtags = hashtags;
        this.comments = comments;
        this.creationDate = creationDate;
        this.reports = reports;
        this.visibility = visibility;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public List<MultiMedia> getContent() {
        return content;
    }

    public Reactions getReactions() {
        return reactions;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public List<Long> getReports() {
        return reports;
    }

    public Visibility getVisibility() {
        return visibility;
    }
}
