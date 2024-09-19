package com.ralph.coupon.calculate.api.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: Product
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-03 22:33
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    /**
     * 商品ID
     */
    private Long projectId;

    /**
     * 商品价格
     */
    private long price;

    /**
     * 商品在购物车中的数量
     */
    private Integer count;

    /**
     * 商品销售门店
     */
    private Long shopId;


}
