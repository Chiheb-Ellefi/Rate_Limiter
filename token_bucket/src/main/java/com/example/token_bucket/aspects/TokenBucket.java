package com.example.token_bucket.aspects;


import com.example.token_bucket.exceptions.RateLimitReachedException;
import com.example.token_bucket.exceptions.UnauthenticatedException;

import com.example.token_bucket.services.MainService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

@Component
@Aspect
@Order(2)
public class TokenBucket {
    private final Logger logger = Logger.getLogger(TokenBucket.class.getName());
    private final MainService mainService;
    private final  HttpServletRequest request;
    @Value("${spring.application.refresh_rate}")
    private  Integer refresh_rate;

    @Autowired
    public TokenBucket(HttpServletRequest request,MainService mainService) {
        this.mainService =mainService ;
        this.request = request;


    }

    private Cookie getCookie(String name) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    @Around("@annotation(com.example.token_bucket.annotations.RateLimitByIP)")
    Object rateLimitByIP(ProceedingJoinPoint joinPoint) throws Throwable {
        String ip = request.getRemoteAddr();
       Long value = mainService.decreaseIpAddress(ip);
       if(value <0){
           throw new  RateLimitReachedException(String.format("Too many request where sent from the same ip address %s try in %d seconds.",ip,refresh_rate));
       }
        return joinPoint.proceed();


    }
    @Around("@annotation(com.example.token_bucket.annotations.RateLimitByUser)")
    Object rateLimitByUser(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Rate limiting by user");
        Cookie cookie=getCookie("userId");
        if(cookie==null){
            throw new UnauthenticatedException();
        }
        Long value=mainService.decreaseUserToken(cookie.getValue());
        if ( value < 0){

            throw new  RateLimitReachedException(String.format("Too many request where sent by the same user try in %d seconds.",refresh_rate));
        }

        return joinPoint.proceed();


    }
    @Around("@annotation(com.example.token_bucket.annotations.RateLimitByDevice)")
    Object rateLimitByDevice(ProceedingJoinPoint joinPoint) throws Throwable {
         logger.info("Rate limiting by device");
         Cookie cookie=getCookie("deviceId");
        if(cookie!=null){
            Long value=mainService.decreaseDeviceId(cookie.getValue());
            if ( value < 0){
                throw new  RateLimitReachedException(String.format("Too many request where sent from the same device try in %d seconds.",refresh_rate));
            }
        }
        return joinPoint.proceed();


    }



}


