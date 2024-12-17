package com.example.token_bucket.services;


import com.example.token_bucket.repositories.RateLimitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class MainServiceUnitTests {

    @Mock
    private  RateLimitRepository rateLimitRepository;

    @Mock
    private  DefaultRedisScript<Long> redisScript;


    @InjectMocks
    private MainService mainService;

    @Test
    void decreaseUserTokenHappyFlow() {
        String token = "token_test";
        Long userId_bucket = 10L;

        // Mock repository behavior with proper matchers
        Mockito.when(rateLimitRepository.executeScript(
                eq(redisScript),
                Mockito.anyList(),
                Mockito.anyString()
        )).thenReturn(userId_bucket);

        // Call method
        Long result = mainService.decreaseUserToken(token);

        // Assert result
        assertEquals(userId_bucket, result);

        // Verify method invocation
        verify(rateLimitRepository).executeScript(
                eq(redisScript),
                Mockito.anyList(),
                Mockito.anyString()
        );
    }



}
