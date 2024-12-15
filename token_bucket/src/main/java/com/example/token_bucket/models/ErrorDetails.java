package com.example.token_bucket.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorDetails {

    private String message;
    private String details;



}
