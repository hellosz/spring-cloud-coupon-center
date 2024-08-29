package com.ralph.coupon.template.api.beans.rules;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: Discount
 * @description: 折扣规则
 * @author: Neng.Tian
 * @create: 2024-08-29 23:03
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
    /**
     * 满减 - 减掉的钱
     * 折扣 - 90 表示 9 折，95 表示 95 折扣
     */
    private Long quota;

    /**
     * 消费门槛
     */
    private Long threshold;
}
