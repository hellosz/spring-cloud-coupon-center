package com.ralph.coupon.customer.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: FeignConfig
 * @description:
 * @author: Neng.Tian
 * @create: 2024-10-09 11:24
 **/
@Configuration
public class FeignConfig {

    /**
     * 在 开发和测试环境，增加日志
     *
     * @return
     */
    @Bean
    Logger.Level feignLogger() {
        return Logger.Level.FULL;
     }
}
