package com.example.searchservice.Model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;


@Data
@Entity
public class SearchService {
    @javax.persistence.Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column
    private String userId;
    @Column
    private String text;


}

