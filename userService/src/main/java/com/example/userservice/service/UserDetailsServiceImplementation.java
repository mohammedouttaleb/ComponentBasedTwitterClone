package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
    @Autowired
    UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return  com.example.userservice.service.UserDetailsImplementation.build(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }


    @Transactional
    public void updateUser(long id, User user) {
        User oldUser = userRepository.findById(id).get();
//        System.out.println("OLD = " + oldUser.getNom() + "NEW = " + user.getNom());
        oldUser.setEmail(user.getEmail());
        oldUser.setFullName(user.getFullName());
        oldUser.setLocation(user.getLocation());
        oldUser.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(oldUser);
    }
}
