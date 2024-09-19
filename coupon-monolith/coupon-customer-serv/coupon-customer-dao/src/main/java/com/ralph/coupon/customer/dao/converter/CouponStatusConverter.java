package com.ralph.coupon.customer.dao.converter;

import com.ralph.coupon.customer.api.enums.CouponStatus;

import javax.persistence.AttributeConverter;

/**
 * @ClassName: CouponStatusConverter
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-15 12:48
 **/
public class CouponStatusConverter  implements AttributeConverter<CouponStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CouponStatus couponStatus) {
        return couponStatus.getCode();
    }

    @Override
    public CouponStatus convertToEntityAttribute(Integer code) {
        return CouponStatus.convert(code);
    }
}