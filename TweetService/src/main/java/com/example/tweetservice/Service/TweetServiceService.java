package com.example.tweetservice.Service;

import com.example.tweetservice.Dto.TweetServiceDto;
import com.example.tweetservice.Model.TweetService;
import com.example.tweetservice.Repository.TweetServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TweetServiceService {

    @Autowired
    private TweetServiceRepository tweetServiceRepository;

    public Optional<List<TweetService>> getAllTweetsByUser(Long userId){
        return tweetServiceRepository.findByCreatorId(userId);
    }

    public Optional<TweetService> getTweetById(Long Id){
        return tweetServiceRepository.findById(Id);
    }

    public List<TweetService> getAllTweets() {
        return tweetServiceRepository.findAll();
    }
}
