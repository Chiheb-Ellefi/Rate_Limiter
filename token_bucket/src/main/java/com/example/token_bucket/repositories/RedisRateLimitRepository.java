package com.example.token_bucket.repositories;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RedisRateLimitRepository implements RateLimitRepository {
    private final RedisTemplate<String, String> redisTemplate;
    public RedisRateLimitRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Long executeScript(DefaultRedisScript<Long> redisScript, List<String> keys, Object... args) {
        return redisTemplate.execute(redisScript, keys, args);
    }
}
