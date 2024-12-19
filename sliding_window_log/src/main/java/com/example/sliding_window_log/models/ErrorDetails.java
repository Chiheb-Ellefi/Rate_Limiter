package com.example.sliding_window_log.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorDetails {
    private String message;
    private String details;
}
