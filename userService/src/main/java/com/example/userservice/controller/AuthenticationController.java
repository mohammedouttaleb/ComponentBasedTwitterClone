package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.payload.request.LoginRequest;
import com.example.userservice.payload.request.SignupRequest;
import com.example.userservice.payload.response.JwtResponse;
import com.example.userservice.payload.response.MessageResponse;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.jwt.JwtUtils;
import com.example.userservice.service.UserDetailsImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();
        return ResponseEntity.ok(jwt);
    }


    @PostMapping(value="/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest ){

        // Create new user's account
        User user = new User(signUpRequest.getFullName(), signUpRequest.getEmail(), signUpRequest.getUserName(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getLocation(),
                signUpRequest.getPhoneNumber());

        user = userRepository.save(user);
        final String uri = "http://192.168.43.20:8082/adduser/" + user.getId();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(uri, null, String.class);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/token={token}")
    public Long getCurrentUserId(@PathVariable String token) {
        if (token != null && jwtUtils.validateJwtToken(token)) {
            String email = jwtUtils.getUserNameFromJwtToken(token);
//            System.err.println(email);
            Long userId = userRepository.findByUserName(email).get().getId();
            return userId;
        }
        return null;
    }

    @GetMapping("/id={userId}")
    public User getUserById(@PathVariable Long userId) {
        User user = userRepository.findById(userId).get();
        return user;
    }
}
