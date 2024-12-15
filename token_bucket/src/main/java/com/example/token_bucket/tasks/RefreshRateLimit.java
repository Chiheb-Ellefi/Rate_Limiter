package com.example.token_bucket.tasks;


import com.example.token_bucket.services.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RefreshRateLimit {
    @Value("${spring.application.user_refresh_rate}")
    private  String userRefreshRateLimit;
    @Value("${spring.application.device_refresh_rate}")
    private String deviceRefreshRateLimit;
    @Value("${spring.application.ip_refresh_rate}")
    private String ipRefreshRateLimit;
private final  Logger logger = LoggerFactory.getLogger(RefreshRateLimit.class);

    private final MainService mainService;
    @Autowired
    public RefreshRateLimit(MainService mainService) {
        this.mainService = mainService;
    }

    @Scheduled(fixedRateString = "${spring.application.user_refresh_rate}")
    void refreshUserRateLimit() {
        logger.info("Refresh rate user limit");
       var value= mainService.refreshUserRateLimit();
       logger.info("Refreshed rate user limit: "+value);
    }
    @Scheduled(fixedRateString = "${spring.application.ip_refresh_rate}")
    void refreshIpRateLimit() {
        logger.info("Refresh rate ip limit");
        var value =mainService.refreshIpRateLimit();
        logger.info("Refreshed rate ip limit: "+value);
    }
    @Scheduled(fixedRateString = "${spring.application.device_refresh_rate}")
    void refreshDeviceRateLimit() {
        logger.info("Refresh rate device limit");
        var value =mainService.refreshDeviceRateLimit();
        logger.info("Refreshed rate device limit: "+value);

    }


}
