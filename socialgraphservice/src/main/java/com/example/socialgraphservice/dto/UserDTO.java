package com.example.socialgraphservice.dto;

import com.example.socialgraphservice.model.User;

public class UserDTO {

    private String userTag;

    public UserDTO(User user) {
        this.userTag = user.getUserTag();
    }

}
