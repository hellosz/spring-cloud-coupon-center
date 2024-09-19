package com.ralph.coupon.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName: CustomerApplication
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-15 13:20
 **/
@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = "com.ralph")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.ralph")
@EntityScan(basePackages = "com.ralph")
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
