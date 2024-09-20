package com.ralph.coupon.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: CouponDiscoveryController
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-20 14:38
 **/
@RestController
@RequestMapping("/discovery")
public class CouponDiscoveryController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/getServices")
    public List<String> getServices() {
        return discoveryClient.getServices();
    }

    @GetMapping("getServiceInstances")
    public List<ServiceInstance> getServiceInstances(String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }
}
