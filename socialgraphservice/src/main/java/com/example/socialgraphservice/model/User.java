package com.example.socialgraphservice.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;
import java.util.stream.Collectors;

@Node
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String userTag;

    public User() {
    }

    public User(String userTag) {
        this.userTag = userTag;
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
}
