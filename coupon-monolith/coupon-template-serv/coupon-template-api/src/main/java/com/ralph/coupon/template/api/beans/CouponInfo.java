package com.ralph.coupon.template.api.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: CouponInfo
 * @description: 优惠券信息
 * @author: Neng.Tian
 * @create: 2024-08-29 23:08
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponInfo {

    private Long id;

    private Long templateId;

    private Long userId;

    private Long shopId;

    private Integer status;

    /**
     * 模板信息
     */
    private CouponTemplateInfo template;

}
