package com.example.springmodels.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    @GetMapping()
    private String getHomePage(){
        return "home";
    }

    @GetMapping("/about")
    private String getAboutPage(){
        return "about_us";
    }
}
