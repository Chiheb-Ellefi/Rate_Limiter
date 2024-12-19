package com.example.sliding_window_log.aspects;

import com.example.sliding_window_log.exceptions.RateLimitReachedException;
import com.example.sliding_window_log.services.RateLimiterService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RateLimiter {
private final RateLimiterService rateLimiterService;
@Autowired
public RateLimiter(RateLimiterService rateLimiterService) {
    this.rateLimiterService = rateLimiterService;
}

@Around("@annotation(com.example.sliding_window_log.annotations.RateLimit)")
public Object checkRateLimiter(ProceedingJoinPoint joinPoint) throws Throwable {
    Boolean isValid=rateLimiterService.checkRateLimiter();
    if(isValid){
        return joinPoint.proceed();
    }
    throw new RateLimitReachedException();
}
}
