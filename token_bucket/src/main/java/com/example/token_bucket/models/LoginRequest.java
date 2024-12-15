package com.example.token_bucket.models;


import lombok.Getter;

@Getter
public class LoginRequest {
    private String username;
    private String password;

    public boolean isInvalid() {
        return username == null || password == null;
    }





}
