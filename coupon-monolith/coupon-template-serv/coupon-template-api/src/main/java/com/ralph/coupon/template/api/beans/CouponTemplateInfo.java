package com.ralph.coupon.template.api.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: CouponTemplateInfo
 * @description:
 * @author: Neng.Tian
 * @create: 2024-08-29 23:10
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponTemplateInfo {

    private Long id;

    @NotNull
    private String name;

    /**
     * 描述
     */
    @NotNull
    private String desc;

    /**
     * 优惠券类型
     */
    @NotNull
    private String type;

    /**
     * 适用店铺 - 为空则则表示没有限制
     */
    private String shopId;

    /**
     * 优惠券规则
     */
    @NotNull
    private String rule;

    private Boolean  available;
}
