package com.example.sliding_window_log.exceptions;

import com.example.sliding_window_log.models.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionsHandler {
    @ExceptionHandler(RateLimitReachedException.class)
    public ResponseEntity<ErrorDetails> rateLimitReachedException() {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message("Too Many Requests")
                .details("You have exceeded the allowed number of requests within the allocated time. Please wait for a while before retrying.")
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.TOO_MANY_REQUESTS);
    }
}
