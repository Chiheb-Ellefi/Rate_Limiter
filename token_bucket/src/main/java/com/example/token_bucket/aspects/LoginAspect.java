package com.example.token_bucket.aspects;
import com.example.token_bucket.exceptions.UnauthenticatedException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Aspect
@Order (1)
public class LoginAspect {
    private final  HttpServletRequest request;
    private  final HttpServletResponse response;

    @Value("${spring.application.device_token_ttl}")
    Integer token_ttl;

public LoginAspect(HttpServletRequest request,HttpServletResponse response) {
    this.request = request;
    this.response = response;
}
    @Around("@annotation(com.example.token_bucket.annotations.CheckLoggedIn)")
    Object checkLoggedIn(ProceedingJoinPoint joinPoint) throws Throwable {


        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new UnauthenticatedException();
        }
        Cookie deviceIdCookie = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("deviceId")) {
                deviceIdCookie = cookie;
                break;
            }
        }
       Object res= joinPoint.proceed();

        if (deviceIdCookie == null) {
            deviceIdCookie = new Cookie("deviceId", UUID.randomUUID().toString());
            deviceIdCookie.setHttpOnly(true);
            deviceIdCookie.setSecure(true);
            deviceIdCookie.setMaxAge(token_ttl);
            response.addCookie(deviceIdCookie);

        }
        return res;



    }

}
