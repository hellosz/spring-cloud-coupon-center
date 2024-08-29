package com.ralph.coupon.template.dao.converter;

import com.ralph.coupon.template.api.enums.CouponType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @ClassName: CouponTypeConverter
 * @description:
 * @author: Neng.Tian
 * @create: 2024-08-30 00:30
 **/
@Converter
public class CouponTypeConverter implements AttributeConverter<CouponType, String> {
    @Override
    public String convertToDatabaseColumn(CouponType couponType) {
        return couponType.getCode();
    }

    @Override
    public CouponType convertToEntityAttribute(String code) {
        return CouponType.convert(code);
    }
}
