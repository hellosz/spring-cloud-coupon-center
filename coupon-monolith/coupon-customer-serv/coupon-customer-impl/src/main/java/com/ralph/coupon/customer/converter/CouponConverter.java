package com.ralph.coupon.customer.converter;

import com.ralph.coupon.customer.dao.entity.Coupon;
import com.ralph.coupon.template.api.beans.CouponInfo;

/**
 * @ClassName: CouponConverter
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-15 13:20
 **/
public class CouponConverter {
    public static CouponInfo convertToCoupon(Coupon coupon) {

        return CouponInfo.builder()
                .id(coupon.getId())
                .status(coupon.getStatus().getCode())
                .templateId(coupon.getTemplateId())
                .shopId(coupon.getShopId())
                .userId(coupon.getUserId())
                .template(coupon.getTemplateInfo())
                .build();
    }
}
