package com.ralph.coupon.calculate.impl.service.intf;

import com.ralph.coupon.calculate.api.beans.ShoppingCart;
import com.ralph.coupon.calculate.api.beans.SimulationOrder;
import com.ralph.coupon.calculate.api.beans.SimulationResponse;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName: CouponCalculationService
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-15 12:16
 **/
public interface CouponCalculationService {

    ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart cart);

    SimulationResponse simulateOrder(@RequestBody SimulationOrder cart);
}