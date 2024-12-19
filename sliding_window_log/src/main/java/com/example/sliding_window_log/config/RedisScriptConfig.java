package com.example.sliding_window_log.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Configuration
public class RedisScriptConfig {
    @Bean
    DefaultRedisScript<Boolean> redisScript() {
        DefaultRedisScript<Boolean> script = new DefaultRedisScript<Boolean>();
        script.setResultType(Boolean.class);
        script.setLocation(new ClassPathResource("scripts/refresh_rate_script.lua"));
        return script;
    }
}
