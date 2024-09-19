package com.ralph.coupon.template.impl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @ClassName: TemplateApplication
 * @description: 模板应用
 * @author: Neng.Tian
 * @create: 2024-08-29 22:05
 **/
@SpringBootApplication(scanBasePackages = "com.ralph")
@EnableJpaAuditing
// 会和 CustomerApplication 冲突，所以需要注释掉
@EnableJpaRepositories(basePackages = "com.ralph.coupon.template.dao")
@EntityScan(basePackages = "com.ralph.coupon.template.dao")
public class TemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class, args);
    }
}
