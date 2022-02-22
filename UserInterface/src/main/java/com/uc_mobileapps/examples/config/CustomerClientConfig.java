package com.uc_mobileapps.examples.config;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Configuration;

/**
 * Helper class to let the example run without an additional service discovery instance.
 */
@Configuration
@LoadBalancerClient(name = "customerclient", configuration = CustomerClientConfig.class)
public class CustomerClientConfig {
}

