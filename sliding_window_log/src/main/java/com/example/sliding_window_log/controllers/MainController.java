package com.example.sliding_window_log.controllers;

import com.example.sliding_window_log.annotations.RateLimit;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    @RateLimit
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World");

    }

}
