package com.ralph.coupon.calculate.impl.template;


import com.ralph.coupon.calculate.api.beans.ShoppingCart;

public interface RuleTemplate {

    // 计算优惠券
    ShoppingCart calculate(ShoppingCart settlement);
}
