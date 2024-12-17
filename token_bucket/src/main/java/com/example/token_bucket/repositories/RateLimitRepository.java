package com.example.token_bucket.repositories;

import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.List;

public interface RateLimitRepository {
     Long executeScript(DefaultRedisScript<Long> redisScript, List<String> keys,Object... args);
}
