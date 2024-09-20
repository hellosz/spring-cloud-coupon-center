package com.ralph.coupon.customer.service.intf;

import com.ralph.coupon.calculate.api.beans.ShoppingCart;
import com.ralph.coupon.calculate.api.beans.SimulationOrder;
import com.ralph.coupon.calculate.api.beans.SimulationResponse;
import com.ralph.coupon.customer.api.beans.RequestCoupon;
import com.ralph.coupon.customer.api.beans.SearchCoupon;
import com.ralph.coupon.customer.dao.entity.Coupon;
import com.ralph.coupon.template.api.beans.CouponInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName: CouponCustomerService
 * @description: 用户对接服务
 * @author: Neng.Tian
 * @create: 2024-09-15 13:19
 **/
public interface CouponCustomerService {

    /**
     * 领券接口
     */
    Coupon requestCoupon(RequestCoupon request);

    /**
     * 核销优惠券
     *
     * @param cart
     * @return
     */
    ShoppingCart placeOrder(ShoppingCart cart);

    /**
     * 订单金额试算
     *
     * @param order
     * @return
     */
    SimulationResponse simulateOrderPrice(SimulationOrder order);

    /**
     * 删除优惠券
     *
     * @param userId
     * @param couponId
     */
    void deleteCoupon(Long userId, Long couponId);

    /**
     * 查询用户优惠券
     *
     * @param request
     * @return
     */
    List<CouponInfo> findCoupon(SearchCoupon request);

    /**
     * 请求 calculate 服务
     *
     * @param msg
     * @return
     */
    String retrieveCalculate(HttpServletRequest request, String msg);

    /**
     * 请求 template 服务
     *
     * @param msg
     * @return
     */
    String retrieveTemplate(String msg);
}
