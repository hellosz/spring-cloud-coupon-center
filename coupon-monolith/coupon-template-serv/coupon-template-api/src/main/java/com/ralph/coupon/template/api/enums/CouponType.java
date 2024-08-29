package com.ralph.coupon.template.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 优惠券类型
 */
@Getter
@AllArgsConstructor
public enum CouponType {

    UNKNOWN("unknown", "0"),
    MONEY_OFF("满减券", "1"),
    DISCOUNT("打折", "2"),
    RANDOM_DISCOUNT("随机减", "3"),
    LONELY_NIGHT_MONEY_OFF("晚间双倍优惠券", "4"),
    ANTI_PUA("PUA加倍奉还券", "5");

    private String description;

    private String code;

    /**
     * 转换
     *
     * @param code
     * @return
     */
    public static CouponType convert(String code) {
        for (CouponType value : values()) {
            if (value.code.equalsIgnoreCase(code)) {
                return value;
            }
        }

        return UNKNOWN;
    }
}
