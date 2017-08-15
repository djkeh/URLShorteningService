package com.kakaopay.urlshortening.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main() {
        return "index";
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String mainSubmit(Model model) {
        model.addAttribute("url", "http://kakao.pay/test_URL");
        
        return "index";
    }
    
    
}
