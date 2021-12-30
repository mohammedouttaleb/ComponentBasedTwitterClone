package com.example.searchservice.Repository;

import com.example.searchservice.Model.SearchService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SearchServiceRepository extends JpaRepository<SearchService, String> {
   }
