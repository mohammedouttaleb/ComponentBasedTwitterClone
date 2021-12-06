package com.example.tweetservice.Model.Util;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class MultiMedia {
    @Id
    private String id;
    private String name;
    private String type;
    private byte[] data;

    public MultiMedia(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
