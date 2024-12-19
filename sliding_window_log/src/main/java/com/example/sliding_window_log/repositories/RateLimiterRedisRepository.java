package com.example.sliding_window_log.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collections;
@Repository
public class RateLimiterRedisRepository implements RedisRepository {
    @Value("${spring.application.bucket_size}")
    private String bucketSize;
    @Value("${spring.application.refresh_time}")
    private String refreshTime;
    private final RedisTemplate<String, String> redisTemplate;
    private final DefaultRedisScript<Boolean> redisScript;

    public RateLimiterRedisRepository(RedisTemplate<String, String>  redisTemplate, DefaultRedisScript<Boolean> redisScript) {
        this.redisTemplate = redisTemplate;
        this.redisScript = redisScript;
    }
    @Override
    public Boolean executeScript() {
        String timestamp =String.valueOf(Instant.now().toEpochMilli());
     return redisTemplate.execute(redisScript, Collections.singletonList("bucket"), timestamp,bucketSize,refreshTime);

    }
}
