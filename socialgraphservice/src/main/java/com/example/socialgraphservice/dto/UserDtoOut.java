package com.example.socialgraphservice.dto;

public class UserDtoOut {

    private String userID_1;
    private String userID_2;

    public UserDtoOut(String userID_1, String userID_2) {
        this.userID_1 = userID_1;
        this.userID_2 = userID_2;
    }

    public String getUserID_1() {
        return userID_1;
    }

    public void setUserID_1(String userID_1) {
        this.userID_1 = userID_1;
    }

    public String getUserID_2() {
        return userID_2;
    }

    public void setUserID_2(String userID_2) {
        this.userID_2 = userID_2;
    }
}
