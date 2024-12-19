package com.example.sliding_window_log.repositories;

import org.springframework.data.redis.core.script.DefaultRedisScript;

public interface RedisRepository {
    Boolean executeScript();
}
