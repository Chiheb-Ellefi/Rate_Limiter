package com.example.token_bucket.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RateLimitReachedException extends RuntimeException {
private String details;

}
