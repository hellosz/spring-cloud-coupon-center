package com.ralph.coupon.template.impl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: TemplateApplication
 * @description: 模板应用
 * @author: Neng.Tian
 * @create: 2024-08-29 22:05
 **/
@SpringBootApplication(scanBasePackages = "com.ralph")
public class TemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class, args);
    }
}
