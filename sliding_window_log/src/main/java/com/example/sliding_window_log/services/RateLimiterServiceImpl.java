package com.example.sliding_window_log.services;

import com.example.sliding_window_log.repositories.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {
    private final RedisRepository redisRepository;
    @Autowired
    public RateLimiterServiceImpl(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @Override
    public Boolean checkRateLimiter() {
        return redisRepository.executeScript();

    }
}
