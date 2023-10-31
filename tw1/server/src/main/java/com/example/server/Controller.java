package com.example.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/endpoint")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/login")
    public void login() {

    }
}
