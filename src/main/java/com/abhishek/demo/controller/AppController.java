package com.abhishek.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController
{
    @GetMapping("/")
    public String homePage()
    {
        return "This is home page";
    }
}
