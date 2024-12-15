package com.example.token_bucket.exceptions;

import com.example.token_bucket.models.ErrorDetails;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<ErrorDetails> unauthenticatedExceptionHandler( ) {
        ErrorDetails errorDetails=ErrorDetails.builder()
                .message("No user logged in")
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(RateLimitReachedException.class)
    public ResponseEntity<ErrorDetails> rateLimitReachedExceptionHandler(RateLimitReachedException ex) {
        ErrorDetails errorDetails=ErrorDetails.builder()
                .message("Rate limit reached for this userId, please try again later.")
                .details(ex.getDetails())
                .build();
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(errorDetails);
    }
}
