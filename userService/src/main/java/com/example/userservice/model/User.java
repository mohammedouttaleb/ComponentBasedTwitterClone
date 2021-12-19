package com.example.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String fullName;

    private String email;

    private String userName;

    private String password;

    private String location;

    private String phoneNumber;

    public User(String fullName, String email, String userName, String password, String location, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }
}
