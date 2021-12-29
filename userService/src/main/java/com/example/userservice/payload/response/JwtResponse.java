package com.example.userservice.payload.response;

import com.example.userservice.model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private long id;
    private  String fullName;
    private String email;
    private String password;
    private String location;
    private String phoneNumber;

    public JwtResponse(String token, long id, String fullName, String email, String password, String location, String phoneNumber) {
        this.token = token;
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }
}
