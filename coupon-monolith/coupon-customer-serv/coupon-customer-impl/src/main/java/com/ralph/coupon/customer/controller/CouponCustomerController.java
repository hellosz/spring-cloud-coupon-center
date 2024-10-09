package com.ralph.coupon.customer.controller;

import com.alibaba.fastjson.JSON;
import com.ralph.coupon.calculate.api.beans.ShoppingCart;
import com.ralph.coupon.calculate.api.beans.SimulationOrder;
import com.ralph.coupon.calculate.api.beans.SimulationResponse;
import com.ralph.coupon.customer.api.beans.RequestCoupon;
import com.ralph.coupon.customer.api.beans.SearchCoupon;
import com.ralph.coupon.customer.dao.entity.Coupon;
import com.ralph.coupon.customer.service.intf.CouponCustomerService;
import com.ralph.coupon.template.api.beans.CouponInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName: CouponCustomerController
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-15 13:17
 **/

@Slf4j
@RestController
@RefreshScope // 动态刷新
@RequestMapping("coupon-customer")
public class CouponCustomerController {

    @Autowired
    private CouponCustomerService service;

    /**
     * 关闭 coupon 申请入口
     *
     */
    @Value("${biz.disableCoupon:false}")
    private Boolean disableCoupon;

    @PostMapping("/requestCoupon")
    public Coupon requestCoupon(@Valid @RequestBody RequestCoupon request) {
        log.info("requestCoupon:{}", JSON.toJSONString(request));

        // 动态开关
        if (disableCoupon) {
            log.info("requestCoupon disabled");
            return new Coupon();
        }

        return service.requestCoupon(request);
    }

    @PostMapping("/deleteCoupon")
    public void deleteCoupon(@RequestParam("userId") Long userId, @RequestParam("couponId") Long couponId) {
        log.info("deleteCoupon:{},{}", userId, couponId);

        service.deleteCoupon(userId, couponId);
    }

    @PostMapping("simulateOrder")
    public SimulationResponse simulate(@Valid @RequestBody SimulationOrder order) {
        log.info("simulateOrder: {}", JSON.toJSONString(order));

        return service.simulateOrderPrice(order);
    }

    @PostMapping("/placeOrder")
    public ShoppingCart checkout(@Valid @RequestBody ShoppingCart cart) {
        log.info("placeOrder: {}", JSON.toJSONString(cart));

        return service.placeOrder(cart);
    }

    @PostMapping("findCoupon")
    public List<CouponInfo> findCoupon(@Valid @RequestBody SearchCoupon request) {
        log.info("fndCoupon:{}", JSON.toJSONString(request));

        return service.findCoupon(request);
    }

    @GetMapping("/retrieveCalculate")
    public String retrieveCalculate(HttpServletRequest request, @RequestParam("msg") String msg) {
        log.info("retrieve msg: {}", msg);

        return service.retrieveCalculate(request, msg);
    }

    @GetMapping("/retrieveTemplate")
    public String retrieveTemplate(@RequestParam("msg") String msg) {
        log.info("retrieve msg: {}", msg);

        return service.retrieveTemplate(msg);
    }

    @GetMapping("/timeout")
    public String timeOut(@RequestParam(value = "timeout", required = false) Integer timeout) {
        log.info("timeout: {}", timeout);

        return service.timeout(timeout);
    }

    @GetMapping("/randomBreak")
    public String randomBreak(@RequestParam("factor") Integer factor) {
        log.info("randomBreak");

        return service.randomBreak(factor);
    }


}
