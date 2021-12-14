package com.geekylikes.app.controllers;

import com.geekylikes.app.payloads.api.response.Article;
import com.geekylikes.app.payloads.api.response.NewsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${geekylikes.app.newsApiKey}")
    private String apiKey;

    @GetMapping("/news/{q}")
    public ResponseEntity<?> getNewsArticles(@PathVariable String q){
        String uri = "https://newsapi.org/v2/everything?"+ "sortBy=popularity" + "&q=" + q + "&apiKey=" + apiKey;
        List<Article> response = restTemplate.getForObject(uri, NewsResponse.class).getArticles();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/newsCategory/{c}")
    public ResponseEntity<?> getNewsArticlesCategory(@PathVariable String c){
        String uri = "https://newsapi.org/v2/everything?"+ "sortBy=popularity" + "&category=" + c + "&apiKey=" + apiKey;
        List<Article> response = restTemplate.getForObject(uri, NewsResponse.class).getArticles();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/all")
    public String allAccess() {
        return "public content";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User content";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String modAccess() {
        return "Mod content";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "admin content";
    }

}
