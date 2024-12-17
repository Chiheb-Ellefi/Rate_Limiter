package com.example.token_bucket.services;

import com.example.token_bucket.repositories.RateLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

    private final RateLimitRepository rateLimitRepository;
    private final DefaultRedisScript<Long> redisScript;
    private final DefaultRedisScript<Long> refreshRedisScript;

    @Autowired
    MainService(RateLimitRepository rateLimitRepository,
                @Qualifier("redisScript") DefaultRedisScript<Long> redisScript,
                @Qualifier("refreshRedisScript") DefaultRedisScript<Long> refreshRedisScript) {
        this.rateLimitRepository = rateLimitRepository;
        this.redisScript = redisScript;
        this.refreshRedisScript = refreshRedisScript;

    }

   public Long decreaseUserToken(String token){
       return rateLimitRepository.executeScript(redisScript, Collections.singletonList("user:"+token),String.valueOf(userId_bucket));

   }
   public Long decreaseIpAddress(String ip){
       return rateLimitRepository.executeScript(redisScript, Collections.singletonList("ip:"+ip),String.valueOf(ipAddress_bucket));

   }
   public Long decreaseDeviceId(String deviceId){
        return rateLimitRepository.executeScript(redisScript,Collections.singletonList("device:"+deviceId),String.valueOf(deviceId_bucket),String.valueOf(deviceId_TTL));
   }

   public Long refreshUserRateLimit(){
       return  rateLimitRepository.executeScript(refreshRedisScript,Collections.singletonList("user:*"),String.valueOf(userId_bucket));
   }
    public Long refreshDeviceRateLimit(){
      return   rateLimitRepository.executeScript(refreshRedisScript,Collections.singletonList("device:*"),String.valueOf(deviceId_bucket),String.valueOf(deviceId_TTL));
    }
    public Long refreshIpRateLimit(){
       return  rateLimitRepository.executeScript(refreshRedisScript,Collections.singletonList("ip:*"),String.valueOf(ipAddress_bucket));
    }




}
