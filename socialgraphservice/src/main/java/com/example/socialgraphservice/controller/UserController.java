package com.example.socialgraphservice.controller;

import com.example.socialgraphservice.dto.UserDTO;
import com.example.socialgraphservice.model.User;
import com.example.socialgraphservice.repository.UserRepository;
import com.example.socialgraphservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/follow/{tagFollower}/fld={tagFollowed}")
    public void follow(@PathVariable("tagFollower") String tagFollower, @PathVariable("tagFollowed") String tagFollowed){
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

    @GetMapping("/follows/{userTag}")
    public Set<User> getFollows(@PathVariable("userTag") String userTag) {
        return userRepository.findByUserTag(userTag).follows;
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
