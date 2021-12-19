package com.example.socialgraphservice.repository;


import com.example.socialgraphservice.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    User findByUserTag(String userTag);
    User getById(Long id);

    List<User> findByFollowsUserTag(String userTag); //returns followers
    List<User> findByBlocksUserTag(String userTag); //returns blockers
    List<User> findByMutesUserTag(String userTag); //returns muters
    List<User> findByReportsUserTag(String userTag); //returns reporters

}
