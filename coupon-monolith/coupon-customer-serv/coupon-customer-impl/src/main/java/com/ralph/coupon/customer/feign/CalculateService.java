package com.ralph.coupon.customer.feign;

import com.ralph.coupon.calculate.api.beans.ShoppingCart;
import com.ralph.coupon.calculate.api.beans.SimulationOrder;
import com.ralph.coupon.calculate.api.beans.SimulationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName: CalculateService
 * @description:
 * @author: Neng.Tian
 * @create: 2024-10-08 23:21
 **/
@FeignClient(name = "coupon-calculate-serv", path = "/calculator")
public interface CalculateService {

    /**
     * 计算订单金额
     *
     * @param cart
     * @return
     */
    @PostMapping("/checkout")
    ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart cart);

    /**
     * 优惠券试算
     *
     * @param simulator
     * @return
     */
    @PostMapping("/simulate")
    SimulationResponse simulateOrder(@RequestBody SimulationOrder simulator);
}
