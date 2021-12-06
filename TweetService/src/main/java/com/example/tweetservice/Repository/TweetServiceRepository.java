package com.example.tweetservice.Repository;

import com.example.tweetservice.Model.TweetService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetServiceRepository extends MongoRepository<TweetService, Long> {
    Optional<List<TweetService>> findByCreatorId(Long creatorId);
}
