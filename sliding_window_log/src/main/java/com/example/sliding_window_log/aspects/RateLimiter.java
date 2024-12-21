package com.example.sliding_window_log.aspects;

import com.example.sliding_window_log.exceptions.RateLimitReachedException;
import com.example.sliding_window_log.services.RateLimiterService;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RateLimiter {
private final RateLimiterService rateLimiterService;
private final HttpServletResponse httpServletResponse;
@Value("${spring.application.bucket_size}")
private  String bucket_size;
@Value("${spring.application.refresh_time}")
private Long refresh_time;

@Autowired
public RateLimiter(RateLimiterService rateLimiterService, HttpServletResponse httpServletResponse) {
    this.rateLimiterService = rateLimiterService;
    this.httpServletResponse = httpServletResponse;
}

@Around("@annotation(com.example.sliding_window_log.annotations.RateLimit)")
public Object checkRateLimiter(ProceedingJoinPoint joinPoint) throws Throwable {
    Boolean isValid=rateLimiterService.checkRateLimiter();
    httpServletResponse.setHeader("X-Ratelimit-Limit",bucket_size);
    httpServletResponse.setHeader("X-Ratelimit-Retry-After",String.format("%d s",refresh_time/1000));
    if(isValid){
        return joinPoint.proceed();
    }
    throw new RateLimitReachedException();
}
}
