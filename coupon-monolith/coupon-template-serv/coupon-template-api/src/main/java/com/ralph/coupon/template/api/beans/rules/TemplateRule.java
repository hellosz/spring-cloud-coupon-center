package com.ralph.coupon.template.api.beans.rules;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: TemplateRule
 * @description: 模板规则
 * @author: Neng.Tian
 * @create: 2024-08-29 23:05
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateRule {

    /**
     * 折扣配置信息
     */
    private Discount discount;

    /**
     * 限制 - 每人可以领取优惠券数量
     */
    private Integer limitation;

    /**
     * 过期时间
     */
    private Long deadline;
}
