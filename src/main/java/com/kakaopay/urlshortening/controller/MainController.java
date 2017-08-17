package com.kakaopay.urlshortening.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kakaopay.urlshortening.service.URLShorteningService;


@Controller
public class MainController {
    
    private final URLShorteningService urlShorteningService;
    
    public MainController(URLShorteningService urlShorteningService) {
        this.urlShorteningService = urlShorteningService;
    }

    @GetMapping("/")
    public String main() {
        return "index";
    }
    
    @PostMapping("/")
    public String mainSubmit(
            @RequestParam String url,
            Model model
            ) {
        if(urlShorteningService.isShortenedURL(url)) {
            String redirectURL = urlShorteningService.restoreURL(url);
            return "redirect:" + redirectURL;
        }
        
        model.addAttribute("url", urlShorteningService.shortenURL(url));
        
        return "index";
    }
    
}
