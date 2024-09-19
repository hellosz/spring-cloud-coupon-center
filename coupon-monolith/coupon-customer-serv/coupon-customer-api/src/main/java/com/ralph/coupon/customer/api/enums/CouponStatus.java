package com.ralph.coupon.customer.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @ClassName: CouponStatus
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-15 12:41
 **/
@Getter
@AllArgsConstructor
public enum CouponStatus {

    AVAILABLE("未使用", 1),
    USED("已使用", 2),
    INACTIVE("已经注销", 3);
    private final String desc;
    private final Integer code;

    /**
     * Coupon 类型转换
     *
     * @param code
     * @return
     */
    public static CouponStatus convert(Integer code) {
       return Arrays.stream(values())
               .filter(bean -> bean.code.equals(code))
               .findAny().orElse(null);
    }
}
