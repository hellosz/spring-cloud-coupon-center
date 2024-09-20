package com.ralph.coupon.customer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @ClassName: WebClientConfig
 * @description: webclient 模块
 * @author: Neng.Tian
 * @create: 2024-09-20 09:55
 **/
@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder register() {
        return WebClient.builder();
    }
}
