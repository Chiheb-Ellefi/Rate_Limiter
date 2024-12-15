package com.example.token_bucket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

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
    private final DefaultRedisScript<Long> refreshRedisScript;

    @Autowired
    MainService(RedisTemplate<String, String> redisTemplate,
                @Qualifier("redisScript") DefaultRedisScript<Long> redisScript,
                @Qualifier("refreshRedisScript") DefaultRedisScript<Long> refreshRedisScript) {
        this.redisTemplate = redisTemplate;
        this.redisScript = redisScript;
        this.refreshRedisScript = refreshRedisScript;

    }

   public Long decreaseUserToken(String token){
       return redisTemplate.execute(redisScript, Collections.singletonList("user:"+token),String.valueOf(userId_bucket));

   }
   public Long decreaseIpAddress(String ip){
       return redisTemplate.execute(redisScript, Collections.singletonList("ip:"+ip),String.valueOf(ipAddress_bucket));

   }
   public Long decreaseDeviceId(String deviceId){
        return redisTemplate.execute(redisScript,Collections.singletonList("device:"+deviceId),String.valueOf(deviceId_bucket),String.valueOf(deviceId_TTL));
   }

   public Long refreshUserRateLimit(){
       return  redisTemplate.execute(refreshRedisScript,Collections.singletonList("user:*"),String.valueOf(userId_bucket));
   }
    public Long refreshDeviceRateLimit(){
      return   redisTemplate.execute(refreshRedisScript,Collections.singletonList("device:*"),String.valueOf(deviceId_bucket),String.valueOf(deviceId_TTL));
    }
    public Long refreshIpRateLimit(){
       return  redisTemplate.execute(refreshRedisScript,Collections.singletonList("ip:*"),String.valueOf(ipAddress_bucket));
    }




}
