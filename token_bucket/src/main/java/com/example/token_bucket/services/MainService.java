package com.example.token_bucket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MainService {
    @Value("${spring.application.userId_bucket}")
    private  String userId_bucket;
    @Value("${spring.application.ipAddress_bucket}")
    private Long ipAddress_bucket;
    @Value("${spring.application.deviceId_bucket}")
    private Long deviceId_bucket;
    @Value("${spring.application.device_token_ttl}")
    private Long deviceId_TTL;

    private final RedisTemplate<String, String> redisTemplate;
    private final DefaultRedisScript<Long> redisScript;

    @Autowired
    MainService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisScript = new DefaultRedisScript<>();
        this.redisScript.setLocation(new ClassPathResource("scripts/rate_limit.lua"));
        this.redisScript.setResultType(Long.class);
    }
   public void addUserToken(String token){
        redisTemplate.opsForValue().set("user:"+token,userId_bucket);
   }
   public Long decreaseUserToken(String token){
        return redisTemplate.opsForValue().decrement("user:"+token);
   }
   public Long decreaseIpAddress(String ip){
       return redisTemplate.execute(redisScript, Collections.singletonList("ip:"+ip),String.valueOf(ipAddress_bucket));

   }
   public Long decreaseDeviceId(String deviceId){
        return redisTemplate.execute(redisScript,Collections.singletonList("device:"+deviceId),String.valueOf(deviceId_bucket),String.valueOf(deviceId_TTL));
   }




}
