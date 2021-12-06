package com.example.tweetservice.Model.Util;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Comments {
    @Id
    private String id;
    private Long userId;
    private Long tweetId;


}
