package com.ralph.coupon.calculate.api.beans;

import com.ralph.coupon.template.api.beans.CouponInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName: ShoppingCart
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-03 22:33
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {

    @NotEmpty
    private List<Product> products;

    private Long couponId;

    private long cost;

    private List<CouponInfo> couponInfos;

    @NotNull
    private Long userId;
}
