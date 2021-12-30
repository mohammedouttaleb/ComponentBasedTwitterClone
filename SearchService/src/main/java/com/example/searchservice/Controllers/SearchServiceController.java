package com.example.searchservice.Controllers;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController(value = "/TweetServices")
@Validated
public class SearchServiceController {

    @GetMapping("GetSearchHistory")
    public List<String> loadSearch(){
        List<String> searches = new ArrayList<String>();
        searches.add("AMINE BENTTALEB");
        searches.add("JAOUHAR DERROUICH");
        searches.add("IDRISS ELMASBAHY");
        searches.add("MOHAMED OUTTALEB");

        return searches;
    }
    @GetMapping("/search/{text}")
    public String getTweetByeUserId(@PathVariable("text") String text){
        return "YOU SEARCHED FOR "+text;
    }


}
