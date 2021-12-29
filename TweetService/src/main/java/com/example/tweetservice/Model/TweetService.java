package com.example.tweetservice.Model;

import com.example.tweetservice.Model.Util.Comments;
import com.example.tweetservice.Model.Util.Multimedia;
import com.example.tweetservice.Model.Util.Reactions;
import com.example.tweetservice.Model.Util.Visibility;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "Tweets" )
public class TweetService {
    @Id
    private String id;

    private String creatorId;
    private List<Multimedia> content;
    private Reactions reactions;
    private List<String> hashtags;
    private List<Comments> comments;
    private Instant creationDate;
    private List<String> reports;
    private Visibility visibility = Visibility.ALL;

}

