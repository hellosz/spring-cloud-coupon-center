package com.ralph.coupon.template.impl.converter;

import com.ralph.coupon.template.api.beans.CouponTemplateInfo;
import com.ralph.coupon.template.dao.entity.CouponTemplate;

/**
 * @ClassName: CouponTemplateConverter
 * @description:
 * @author: Neng.Tian
 * @create: 2024-08-30 23:34
 **/
public class CouponTemplateConverter {

    public static CouponTemplateInfo convertToCouponTemplateInfo(CouponTemplate template) {
        CouponTemplateInfo couponTemplateInfo = CouponTemplateInfo.builder()
        		.id(template.getId())
        		.name(template.getName())
        		.desc(template.getDescription())
        		.type(template.getCategory().getCode())
        		.shopId(template.getShopId())
        		.rule(template.getRule())
        		.available(template.getAvailable())
        		.build();
        return couponTemplateInfo;
    }
}
