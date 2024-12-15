package com.example.token_bucket.controllers;
import com.example.token_bucket.annotations.CheckLoggedIn;
import com.example.token_bucket.annotations.RateLimitByDevice;
import com.example.token_bucket.annotations.RateLimitByIP;
import com.example.token_bucket.annotations.RateLimitByUser;
import com.example.token_bucket.models.LoginRequest;
import com.example.token_bucket.services.MainService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;


@Controller
public class MainController {

    private final MainService mainService;
    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }


    @RateLimitByIP
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        if(loginRequest.isInvalid()){
            throw new RuntimeException("Invalid username or password");
        }
        String userId = Base64.getEncoder().encodeToString(loginRequest.getUsername().getBytes());
        Cookie userCookie = new Cookie("userId", userId);
        userCookie.setHttpOnly(true);
        userCookie.setSecure(true);
        response.addCookie(userCookie);
        mainService.decreaseUserToken(userId);
        return ResponseEntity.ok("Login successful");

    }

    @CheckLoggedIn
    @RateLimitByIP
    @GetMapping("/address")
    public ResponseEntity<String> getAddress(HttpServletRequest request) {
       String address= request.getRemoteAddr();
        return ResponseEntity.ok("Hello "+address);
    }

    @CheckLoggedIn
    @RateLimitByUser
    @GetMapping("/user")
    public ResponseEntity<String> getUser(HttpServletRequest request) {
        String name= request.getLocalName();
        return ResponseEntity.ok("Hello "+name);
    }

    @CheckLoggedIn
    @RateLimitByDevice
    @GetMapping("/device")
    public ResponseEntity<String> getDevice(HttpServletRequest request) {
        String device= request.getRemoteHost();
        return ResponseEntity.ok("Hello "+device);
    }

}
