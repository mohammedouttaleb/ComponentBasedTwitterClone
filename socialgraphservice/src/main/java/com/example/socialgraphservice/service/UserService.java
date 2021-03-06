package com.example.socialgraphservice.service;

import com.example.socialgraphservice.dto.UserDTO;
import com.example.socialgraphservice.dto.UserDtoOut;
import com.example.socialgraphservice.model.User;
import com.example.socialgraphservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


@Service
public class UserService {



    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        Assert.notNull(userRepository, "Repository must not be null!");
        this.userRepository = userRepository;
    }


    public boolean isFollower(User follower, User followed) {
        return userRepository.findByFollowsUserTag(followed.getUserTag()).contains(follower);
    }
    public void addFollow(User follower, User followed) {
        follower.isFollowing(followed);
        userRepository.save(follower);
    }
    public void removeFollow(User follower, User followed) {
        follower.removesFollow(followed);
        userRepository.save(follower);
    }
    public void follow(User follower, User followed) throws URISyntaxException {
        if (!isFollower(follower, followed)) {
            System.out.println("NEW FOLLOW");
            addFollow(follower, followed);
            sendCreateRoomRequest(follower,followed);
        }
        else {
            removeFollow(follower, followed);
            System.out.println("REMOVED FOLLOW");
        }
    }
    private void  sendCreateRoomRequest(User follower, User followed) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:"+3000+"/createRoom/";
        URI uri = new URI(baseUrl);

        UserDtoOut userDtoOut = new UserDtoOut(follower.getUserTag(), followed.getUserTag());

        ResponseEntity<String> result = restTemplate.postForEntity(uri, userDtoOut, String.class);
        System.out.println(result);
    }

    public int numFollowers(User user) {
        return userRepository.findByFollowsUserTag(user.getUserTag()).size();
    }

    public int numFollows(User user) {
        return user.follows.size();
    }


    public boolean isMuting(User muter, User muted) {
        return userRepository.findByMutesUserTag(muter.getUserTag()).contains(muter);
    }
    public void addMute(User muter, User muted) {
        muter.isMuting(muted);
        userRepository.save(muter);
    }
    public void removeMute(User muter, User muted) {
        muter.removesFollow(muted);
        userRepository.save(muter);
    }
    public void mute(User muter, User muted) {
        if(isMuting(muter, muted)) {
            addMute(muter, muted);
        }
        else {
            removeMute(muter, muted);
        }
    }

    public boolean isBlocking(User blocker, User blocked) {
        return userRepository.findByBlocksUserTag(blocked.getUserTag()).contains(blocker);
    }
    public void addBlock(User blocker, User blocked) {
        blocker.isBlocking(blocked);
        userRepository.save(blocker);
    }
    public void removeBlock(User blocker, User blocked) {
        blocker.removesFollow(blocked);
        userRepository.save(blocker);
    }
    public void block(User blocker, User blocked) {
        if(isBlocking(blocker, blocked)) {
            addBlock(blocker, blocked);
        }
        else {
            removeBlock(blocker, blocked);
        }
    }

    public boolean isReporting(User reporter, User reported) {
        return userRepository.findByReportsUserTag(reported.getUserTag()).contains(reporter);
    }
    public void addReport(User reporter, User reported) {
        reporter.isReporting(reported);
        userRepository.save(reporter);
    }
    public void removeReport(User reporter, User reported) {
        reporter.removesFollow(reported);
        userRepository.save(reporter);
    }
    public void report(User reporter, User reported) {
        if(isReporting(reporter, reported)) {
            addReport(reporter, reported);
        }
        else {
            removeReport(reporter, reported);
        }
    }
    public int numReports(User user) {
        return userRepository.findByReportsUserTag(user.getUserTag()).size();
    }


    public List<UserDTO> findByFollowsUserTag(String userTag) {
        List<User> followers = userRepository.findByFollowsUserTag(userTag);
        List<UserDTO> followersDTO = new ArrayList<>();
        for(User user: followers) {
            followersDTO.add(new UserDTO(user));
        }
        return followersDTO;
    }

    public Set<User> getFollows(Long userId) {
        System.out.println(userId);
        System.out.println(userId != null);
        Set<User> follows = userRepository.findById(userId).get().follows;
        return follows;
    }

    public Set<Object> getRecommendations(Long userId) {
        Set<User> follows = userRepository.findById(userId).get().follows;
        List<Long> follows_2ndLevel= new ArrayList<>();
        Set<Long> follows_2ndLevelSet = new HashSet<>();
        for (User follow: follows) {
            List<User> recommendations = new ArrayList<>(follow.follows);
            for (User recommendation: recommendations) {
                follows_2ndLevel.add(recommendation.getId());
                follows_2ndLevelSet.add(recommendation.getId());
            }
        }
        Map<Long, Integer> occurrences = new HashMap<>();
        for (Long recommendation: follows_2ndLevelSet) {
            occurrences.put(recommendation, Collections.frequency(follows_2ndLevel, recommendation));
        }
        Comparator<Map.Entry<Long, Integer>> valueComparator = Map.Entry.comparingByValue();
        List<Map.Entry<Long, Integer>> sortedRecommendations = new ArrayList<>(occurrences.entrySet());
        Collections.sort(sortedRecommendations, valueComparator);
        Set<Object> sortedRecommendationsList = new HashSet<>();
        Set<Object> recommendationsSet = new HashSet<>();
        for (Map.Entry<Long, Integer> recommendation: sortedRecommendations) {
            String uri = "http://192.168.43.96:8080/api/auth/id=" + recommendation.getKey();
            RestTemplate restTemplate = new RestTemplate();
            recommendationsSet.add(restTemplate.getForObject(uri, Object.class));
//            assert recommendationsSet != null;
            sortedRecommendationsList.addAll(recommendationsSet);
            if (sortedRecommendationsList.size() > 4) break;
        }
        return sortedRecommendationsList;
    }

    public void addUserById(Long userId) {
        userRepository.save(new User(userId));
    }

    public Set<Long> getFollowsIds(Long userId) {
        Set<User> follows = userRepository.findById(userId).get().follows;
        Set<Long> followsId = new HashSet<>();
        for (User follow: follows) {
            followsId.add(follow.getId());
        }
        return followsId;
    }
}
