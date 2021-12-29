package com.example.socialgraphservice.service;

import com.example.socialgraphservice.dto.UserDTO;
import com.example.socialgraphservice.model.User;
import com.example.socialgraphservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

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
    public void follow(User follower, User followed) {
        if (!isFollower(follower, followed)) {
            System.out.println("NEW FOLLOW");
            addFollow(follower, followed);
        }
        else {
            removeFollow(follower, followed);
            System.out.println("REMOVED FOLLOW");
        }
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
}
