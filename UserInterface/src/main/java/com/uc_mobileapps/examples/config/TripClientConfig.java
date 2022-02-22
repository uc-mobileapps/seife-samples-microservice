package com.uc_mobileapps.examples.config;

import feign.Logger;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Helper class to let the example run without an additional service discovery instance.
 */
@Configuration
@LoadBalancerClient(name = "tripclient", configuration = TripClientConfig.class)
public class TripClientConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}

