package com.example.socialgraphservice.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;
import java.util.stream.Collectors;

@Node
public class User {

    public static final int DISCOVERABLE_TO_ALL = 0;
    public static final int NOT_DISCOVERABLE = 1;
    public static final int DISCOVERABLE_TO_FOLLOWED = 2;
    private int setting;


    @Id
    private Long id;

    private String userTag;

//    private Configuration configuration;

    public User() {
    }

    public int getSetting() {
        return setting;
    }

    public void setSetting(int setting) {
        this.setting = setting;
    }


    public User(String userTag) {
        this.userTag = userTag;
        this.setting = DISCOVERABLE_TO_ALL;
    }

    public User(Long userId) {
        this.id = userId;
        this.userTag = String.valueOf(userId);
        this.setting = DISCOVERABLE_TO_ALL;
    }

    @Relationship(type = "FOLLOWING", direction = Relationship.Direction.OUTGOING)
    public Set<User> follows;

    public void isFollowing(User user) {
        if (follows == null) {
            follows = new HashSet<>();
        }
        follows.add(user);
    }

    public void removesFollow(User user) {
        if (follows.contains(user)) {
            follows.remove(user);
        }
    }

    @Relationship(type = "MUTE", direction = Relationship.Direction.OUTGOING)
    public Set<User> mutes;

    public void isMuting(User user) {
        if (mutes == null) {
            mutes = new HashSet<>();
        }
        mutes.add(user);
    }

    public void removesMute(User user) {
        if (mutes.contains(user)) {
            mutes.remove(user);
        }
    }

    @Relationship(type = "BLOCK", direction = Relationship.Direction.OUTGOING)
    public Set<User> blocks;

    public void isBlocking(User user) {
        if (blocks == null) {
            blocks = new HashSet<>();
        }
        blocks.add(user);
    }

    public void removesBlock(User user) {
        if (blocks.contains(user)) {
            blocks.remove(user);
        }
    }

    @Relationship(type = "REPORT", direction = Relationship.Direction.OUTGOING)
    public Set<User> reports;

    public void isReporting(User user) {
        if (reports == null) {
            reports = new HashSet<>();
        }
        reports.add(user);
    }

    public void removesReport(User user) {
        if (reports.contains(user)) {
            reports.remove(user);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserTag().equals(user.getUserTag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserTag());
    }

    @Override
    public String toString() {
        return this.userTag + "'s teammates => "
                + Optional.ofNullable(this.follows).orElse(
                Collections.emptySet()).stream()
                .map(User::getUserTag)
                .collect(Collectors.toList());
    }

    public String getUserTag() {
        return userTag;
    }

    public void setUserTag(String userTag) {
        this.userTag = userTag;
    }

    public Long getId() {
        return id;
    }
}
