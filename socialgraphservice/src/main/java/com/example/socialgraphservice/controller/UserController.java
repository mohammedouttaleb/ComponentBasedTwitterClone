package com.example.socialgraphservice.controller;

import com.example.socialgraphservice.dto.UserDTO;
import com.example.socialgraphservice.model.User;
import com.example.socialgraphservice.repository.UserRepository;
import com.example.socialgraphservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        Assert.notNull(userRepository, "Repository must not be null!");
        Assert.notNull(userService, "Service must not be null!");
        this.userRepository = userRepository;
        this.userService = userService;
    }

    //POST relations

    @PostMapping("/adduser/{userId}")
    public void addUser(@PathVariable Long userId) {
        userService.addUserById(userId);
    }

    @PostMapping("/follow/{tagFollower}/fld={tagFollowed}")
    public void follow(@PathVariable("tagFollower") String tagFollower, @PathVariable("tagFollowed") String tagFollowed) throws URISyntaxException {
        System.out.println("This is Social Graph Service");
        User follower = userRepository.findByUserTag(tagFollower);
        User followed = userRepository.findByUserTag(tagFollowed);
        userService.follow(follower, followed);
    }
    @PostMapping("/mute/{tagMuter}/mtd={tagMuted}")
    public void mute(@PathVariable("tagMuter") String tagMuter, @PathVariable("tagMuted") String tagMuted){
        User muter = userRepository.findByUserTag(tagMuter);
        User muted = userRepository.findByUserTag(tagMuted);
        userService.mute(muter, muted);
    }
    @PostMapping("/block/{tagBlocker}/bld={tagBlocked}")
    public void block(@PathVariable("tagBlocker") String tagBlocker, @PathVariable("tagBlocked") String tagBlocked){
        User Blocker = userRepository.findByUserTag(tagBlocker);
        User Blocked = userRepository.findByUserTag(tagBlocked);
        userService.block(Blocker, Blocked);
    }
    @PostMapping("/follow/{tagReporter}/rpd={tagReported}")
    public void report(@PathVariable("tagReporter") String tagReporter, @PathVariable("tagReported") String tagReported){
        User reporter = userRepository.findByUserTag(tagReporter);
        User reported = userRepository.findByUserTag(tagReporter);
        userService.report(reporter, reported);
    }


    //GET Lists

    @GetMapping("/followers/{userTag}")
    public List<UserDTO> getFollowers(@PathVariable("userTag") String userTag) {
        return userService.findByFollowsUserTag(userTag);
    }

    @GetMapping("/follows/{userId}")
    public Set<User> getFollows(@PathVariable("userId") Long userId) {
//        return userRepository.findByUserTag(userTag).follows;
        System.out.println(userId);
        return userService.getFollows(userId);
    }
    @GetMapping("/followsIds/{userId}")
    public Set<Long> getFollowsIds(@PathVariable("userId") Long userId) {
//        return userRepository.findByUserTag(userTag).follows;
        System.out.println(userId);
        return userService.getFollowsIds(userId);
    }

    @GetMapping("/mutes/{userTag}")
    public Set<User> getMutes(@PathVariable("userTag") String userTag) {
        return userRepository.findByUserTag(userTag).mutes;
    }

    @GetMapping("/blocks/{userTag}")
    public Set<User> getBlocks(@PathVariable("userTag") String userTag) {
        return userRepository.findByUserTag(userTag).blocks;
    }

    @GetMapping("/reports/{userTag}")
    public Set<User> getReports(@PathVariable("userTag") String userTag) {
        return userRepository.findByUserTag(userTag).reports;
    }

    @GetMapping("/reporters/{userTag}")
    public List<User> getReporters(@PathVariable("userTag") String userTag) {
        return userRepository.findByReportsUserTag(userTag);
    }

    @GetMapping("/recommendations/{userId}")
    public Set<Object> getRecommendations(@PathVariable Long userId) {
        return userService.getRecommendations(userId);
    }

    //GET numbers

    @GetMapping("/numberOfFollows/{userTag}")
    public int getNumberOfFollows(@PathVariable("userTag") String userTag) {
        return userService.numFollows(userRepository.findByUserTag(userTag));
    }

    @GetMapping("/numberOfFollowers/{userTag}")
    public int getNumberOfFollowers(@PathVariable("userTag") String userTag) {
        return userService.numFollowers(userRepository.findByUserTag(userTag));
    }

    @GetMapping("/numberOfReportss/{userTag}")
    public int getNumberOfReportss(@PathVariable("userTag") String userTag) {
        return userService.numReports(userRepository.findByUserTag(userTag));
    }



}
