package com.ralph.coupon.customer.api.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: SearchCoupon
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-15 12:40
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCoupon {

    @NotNull
    private Long userId;
    private Long shopId;
    private Integer couponStatus;
}