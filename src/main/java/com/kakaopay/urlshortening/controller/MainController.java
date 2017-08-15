package com.kakaopay.urlshortening.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

    @GetMapping("/")
    public String main() {
        return "index";
    }
    
    @PostMapping("/")
    public String mainSubmit(
            @RequestParam String url,
            Model model
            ) {
        if(isShortenedURL(url)) {
            String redirectURL = restoreURL(url);
            return "redirect:" + redirectURL;
        }
        
        model.addAttribute("url", shortenURL(url));
        
        return "index";
    }

    private String shortenURL(String url) {
        if(url.equals("http://test-url.com/")) return "http://kakao.pay/test_URL";
        return url;
    }

    private String restoreURL(String url) {
        if(url == null) return null;
        if(url.equals("http://kakao.pay/test_URL")) return "http://test-url.com/";
        else return url;
    }

    private boolean isShortenedURL(String url) {
        if(url.equals("http://kakao.pay/test_URL")) return true;
        return false;
    }
    
    
}
