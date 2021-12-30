package com.example.tweetservice.Service;

import com.example.tweetservice.Model.TweetService;
import com.example.tweetservice.Model.Util.Comments;
import com.example.tweetservice.Model.Util.Reaction;
import com.example.tweetservice.Model.Util.Reactions;
import com.example.tweetservice.Model.Util.Visibility;
import com.example.tweetservice.Repository.TweetServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class TweetServiceService {

    @Autowired
    private TweetServiceRepository tweetServiceRepository;

    public void createTweet(TweetService tweetService) {
        tweetServiceRepository.save(tweetService);
    }

    public Optional<List<TweetService>> getAllTweetsByUser(String userId) {
        return tweetServiceRepository.findByCreatorId(userId);
    }

    public Optional<TweetService> getTweetById(String Id) {
        return tweetServiceRepository.findById(Id);
    }

    public List<TweetService> getAllTweets() {
        return tweetServiceRepository.findAll();
    }

    public void addReaction(String tweetId, Reaction reactionType, String userId) throws Exception {
        if (tweetServiceRepository.findById(tweetId).isEmpty()) {
            throw new Exception("Tweet not found");
        } else {
            TweetService tweet = tweetServiceRepository.findById(tweetId).get();

            if (reactionType == Reaction.LIKE) {
                if (tweet.getReactions().getLikes().contains(userId)) {
                    tweet.getReactions().getLikes().remove(userId);
                } else {
                    tweet.getReactions().getLikes().add(userId);
                    tweet.setReactions(tweet.getReactions());
                }
            } else if ((reactionType == Reaction.RETWEET)) {
                if (tweet.getReactions().getRetweets().contains(userId)) {
                    tweet.getReactions().getRetweets().remove(userId);
                } else {
                    tweet.getReactions().getRetweets().add(userId);
                    tweet.setReactions(tweet.getReactions());
                }
            }
            tweetServiceRepository.save(tweet);
        }
    }

    public void updateTweet(TweetService tweetService) throws Exception {
        if (tweetServiceRepository.findById(tweetService.getId()).isEmpty()) {
            throw new Exception("Tweet not found");
        } else {
            TweetService tweet = tweetServiceRepository.findById(tweetService.getId()).get();
            tweet.setVisibility(tweetService.getVisibility());
            tweet.setContent(tweetService.getContent());
            tweetServiceRepository.save(tweet);
        }
    }

    public void addComment(Comments comment) throws Exception {
        if (tweetServiceRepository.findById(comment.getTweetId()).isEmpty()) {
            throw new Exception("Tweet not found");
        } else {
            TweetService tweet = tweetServiceRepository.findById(comment.getTweetId()).get();
            tweet.getComments().add(comment);
            tweetServiceRepository.save(tweet);
        }
    }

    public void updateVisibility(String tweetId, Visibility visibility) throws Exception {
        if (tweetServiceRepository.findById(tweetId).isEmpty()) {
            throw new Exception("Tweet not found");
        } else {
            TweetService tweet = tweetServiceRepository.findById(tweetId).get();
            tweet.setVisibility(visibility);
            tweetServiceRepository.save(tweet);

        }
}}
