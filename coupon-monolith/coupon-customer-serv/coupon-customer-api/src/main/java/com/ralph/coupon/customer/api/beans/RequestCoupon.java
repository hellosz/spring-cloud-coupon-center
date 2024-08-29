package com.ralph.coupon.customer.api.beans;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: RequestCoupon
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-15 12:32
 **/
@Data
public class RequestCoupon {

    // 用户领券
    @NotNull
    private Long userId;


    // 券模板ID
    @NotNull
    private Long couponTemplateId;
}
