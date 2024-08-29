package com.ralph.coupon.calculate.impl.controller;

import com.alibaba.fastjson.JSON;
import com.ralph.coupon.calculate.api.beans.ShoppingCart;
import com.ralph.coupon.calculate.api.beans.SimulationOrder;
import com.ralph.coupon.calculate.api.beans.SimulationResponse;
import com.ralph.coupon.calculate.impl.service.intf.CouponCalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: CouponCalculationController
 * @description: coupon 试算服务
 * @author: Neng.Tian
 * @create: 2024-09-03 23:33
 **/
@Slf4j
@RestController
@RequestMapping("/calculator")
public class CouponCalculationController {

    @Autowired
    private CouponCalculationService couponCalculationService;

    @PostMapping("/checkout")
    public ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart  settlement) {
        log.info("do calculation: {}", JSON.toJSONString(settlement));

        return couponCalculationService.calculateOrderPrice(settlement);
    }

    @PostMapping("/simulate")
    public SimulationResponse simulate(@RequestBody SimulationOrder simulator) {
       log.info("do simulation:{}", JSON.toJSONString(simulator));

       return couponCalculationService.simulateOrder(simulator);
    }
}
