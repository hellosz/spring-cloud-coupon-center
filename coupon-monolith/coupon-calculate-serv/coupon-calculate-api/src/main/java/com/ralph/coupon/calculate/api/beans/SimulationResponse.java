package com.ralph.coupon.calculate.api.beans;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @ClassName: SimulationResponse
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-03 22:34
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimulationResponse {

    /**
     * 最省钱的 coupon
     */
    private Long bestCouponId;

    /**
     * 每一个 coupon 对应的 order 价格
     */
    private Map<Long, Long> couponToOrderPrice = Maps.newHashMap();
}
