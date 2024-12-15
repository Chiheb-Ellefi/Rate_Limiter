package com.example.token_bucket.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Configuration
public class RedisScriptConfig {

    @Bean("redisScript")
    public DefaultRedisScript<Long> redisScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("scripts/rate_limit.lua"));
        redisScript.setResultType(Long.class);
        return redisScript;
    }
    @Bean("refreshRedisScript")
    public DefaultRedisScript<Long> refreshRedisScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("scripts/refresh_rate_limit.lua"));
        redisScript.setResultType(Long.class);
        return redisScript;
    }

}
