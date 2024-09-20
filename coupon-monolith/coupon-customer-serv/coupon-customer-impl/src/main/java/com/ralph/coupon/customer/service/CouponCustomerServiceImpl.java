package com.ralph.coupon.customer.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ralph.coupon.calculate.api.beans.ShoppingCart;
import com.ralph.coupon.calculate.api.beans.SimulationOrder;
import com.ralph.coupon.calculate.api.beans.SimulationResponse;
import com.ralph.coupon.customer.api.beans.RequestCoupon;
import com.ralph.coupon.customer.api.beans.SearchCoupon;
import com.ralph.coupon.customer.api.enums.CouponStatus;
import com.ralph.coupon.customer.converter.CouponConverter;
import com.ralph.coupon.customer.dao.CouponDao;
import com.ralph.coupon.customer.dao.entity.Coupon;
import com.ralph.coupon.customer.service.intf.CouponCustomerService;
import com.ralph.coupon.template.api.beans.CouponInfo;
import com.ralph.coupon.template.api.beans.CouponTemplateInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: CouponCustomerServiceImpl
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-15 13:19
 **/
@Slf4j
@Service
public class CouponCustomerServiceImpl implements CouponCustomerService {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private WebClient.Builder webclientBuilder;

    /**
     * 领券接口
     *
     * @param request
     */
    @Override
    public Coupon requestCoupon(RequestCoupon request) {
        CouponTemplateInfo templateInfo = getTemplate(request.getCouponTemplateId());

        if (templateInfo == null) {
            log.error("invalid template id={}", request.getCouponTemplateId());
            throw new IllegalArgumentException("Invalid template id");
        }

        // 模板不能过期
        long now = Calendar.getInstance().getTimeInMillis();
        Long expTime = templateInfo.getRule().getDeadline();
        if (expTime != null && now >= expTime || BooleanUtils.isFalse(templateInfo.getAvailable())) {
            log.error("template is not available id={}", request.getCouponTemplateId());
            throw new IllegalArgumentException("template is unavailable");
        }

        // 用户领券数量超过上限
        Long count = couponDao.countByUserIdAndTemplateId(request.getUserId(), request.getCouponTemplateId());
        if (count >= templateInfo.getRule().getLimitation()) {
            log.error("exceeds maximum number");
            throw new IllegalArgumentException("exceeds maximum number");
        }

        Coupon coupon = Coupon.builder()
                .templateId(request.getCouponTemplateId())
                .userId(request.getUserId())
                .shopId(templateInfo.getShopId())
                .status(CouponStatus.AVAILABLE)
                .build();
        couponDao.save(coupon);
        return coupon;
    }

    /**
     * 核销优惠券
     *
     * @param order
     * @return
     */
    @Transactional
    @Override
    public ShoppingCart placeOrder(ShoppingCart order) {
        if (CollectionUtils.isEmpty(order.getProducts())) {
            log.error("invalid check out request, order={}", order);
            throw new IllegalArgumentException("cart if empty");
        }

        Coupon coupon = null;
        if (order.getCouponId() != null) {
            // 如果有优惠券，验证是否可用，并且是当前客户的
            Coupon example = Coupon.builder()
                    .userId(order.getUserId())
                    .id(order.getCouponId())
                    .status(CouponStatus.AVAILABLE)
                    .build();
            coupon = couponDao.findAll(Example.of(example))
                    .stream()
                    .findFirst()
                    // 如果找不到券，就抛出异常
                    .orElseThrow(() -> new RuntimeException("Coupon not found"));

            CouponInfo couponInfo = CouponConverter.convertToCoupon(coupon);
            couponInfo.setTemplate(getTemplate(coupon.getTemplateId()));
            order.setCouponInfos(Lists.newArrayList(couponInfo));
        }

        // order清算
        ShoppingCart checkoutInfo = calculateOrderPrice(order);

        if (coupon != null) {
            // 如果优惠券没有被结算掉，而用户传递了优惠券，报错提示该订单满足不了优惠条件
            if (CollectionUtils.isEmpty(checkoutInfo.getCouponInfos())) {
                log.error("cannot apply coupon to order, couponId={}", coupon.getId());
                throw new IllegalArgumentException("coupon is not applicable to this order");
            }

            log.info("update coupon status to used, couponId={}", coupon.getId());
            coupon.setStatus(CouponStatus.USED);
            couponDao.save(coupon);
        }

        return checkoutInfo;    }

    /**
     * 订单金额试算
     *
     * @param order
     * @return
     */
    @Override
    public SimulationResponse simulateOrderPrice(SimulationOrder order) {
        List<CouponInfo> couponInfos = Lists.newArrayList();
        // 挨个循环，把优惠券信息加载出来
        // 高并发场景下不能这么一个个循环，更好的做法是批量查询
        // 而且券模板一旦创建不会改内容，所以在创建端做数据异构放到缓存里，使用端从缓存捞template信息
        for (Long couponId : order.getCouponIDs()) {
            Coupon example = Coupon.builder()
                    .userId(order.getUserId())
                    .id(couponId)
                    .status(CouponStatus.AVAILABLE)
                    .build();
            Optional<Coupon> couponOptional = couponDao.findAll(Example.of(example))
                    .stream()
                    .findFirst();
            // 加载优惠券模板信息
            if (couponOptional.isPresent()) {
                Coupon coupon = couponOptional.get();
                CouponInfo couponInfo = CouponConverter.convertToCoupon(coupon);
                couponInfo.setTemplate(getTemplate(coupon.getTemplateId()));
                couponInfos.add(couponInfo);
            }
        }
        order.setCouponInfos(couponInfos);

        // 调用接口试算服务
        return simulateOrder(order);
    }

    /**
     * 删除优惠券
     *
     * @param userId
     * @param couponId
     */
    @Override
    public void deleteCoupon(Long userId, Long couponId) {
        Coupon example = Coupon.builder()
                .userId(userId)
                .id(couponId)
                .status(CouponStatus.AVAILABLE)
                .build();
        Coupon coupon = couponDao.findAll(Example.of(example))
                .stream()
                .findFirst()
                // 如果找不到券，就抛出异常
                .orElseThrow(() -> new RuntimeException("Could not find available coupon"));

        coupon.setStatus(CouponStatus.INACTIVE);
        couponDao.save(coupon);
    }

    /**
     * 查询用户优惠券
     *
     * @param request
     * @return
     */
    @Override
    public List<CouponInfo> findCoupon(SearchCoupon request) {
        // 在真正的生产环境，这个接口需要做分页查询，并且查询条件要封装成一个类
        Coupon example = Coupon.builder()
                .userId(request.getUserId())
                .status(CouponStatus.convert(request.getCouponStatus()))
                .shopId(request.getShopId())
                .build();

        // 这里你可以尝试实现分页查询
        List<Coupon> coupons = couponDao.findAll(Example.of(example));
        if (coupons.isEmpty()) {
            return Lists.newArrayList();
        }

        List<Long> templateIds = coupons.stream()
                .map(Coupon::getTemplateId)
                .collect(Collectors.toList());
        Map<Long, CouponTemplateInfo> templateMap = getTemplateInfoMap(templateIds);
        coupons.stream().forEach(e -> e.setTemplateInfo(templateMap.get(e.getTemplateId())));

        return coupons.stream()
                .map(CouponConverter::convertToCoupon)
                .collect(Collectors.toList());
    }

    @Override
    public String retrieveCalculate(String msg) {
        return webclientBuilder.build()
                .get()
                .uri("http://coupon-calculate-serv/calculator/retrieve?msg=" + msg)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String retrieveTemplate(String msg) {
        return webclientBuilder.build()
                .get()
                .uri("http://coupon-template-serv/template/retrieve?msg=" + msg)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    /**
     * 获取模板信息
     *
     * @param templateId
     * @return
     */
    public CouponTemplateInfo getTemplate(Long templateId) {
        return webclientBuilder.build()
                .get()
                .uri("http://coupon-template-serv/template/getTemplate?id=" + templateId)
                .retrieve()
                .bodyToMono(CouponTemplateInfo.class)
                .block();
    }

    /**
     * 批量获取 Coupon 信息
     *
     * @param ids
     * @return
     */
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids) {
        return webclientBuilder.build()
                .get()
                .uri("http://coupon-template-serv/template/getBatch?ids=" + ids) // 集合拼接字符串，输出结果是什么？
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<Long, CouponTemplateInfo>>() {})
                .block();
    }

    /**
     * 计算订单价格
     *
     * @param cart
     * @return
     */
    public ShoppingCart calculateOrderPrice(ShoppingCart cart) {
        return webclientBuilder.build()
                .post()
                .uri("http://coupon-calculate-serv/calculator/checkout")
                .bodyValue(cart)
                .retrieve()
                .bodyToMono(ShoppingCart.class)
                .block();
    }

    /**
     * 优惠券试算
     *
     * @param simulator
     * @return
     */
    public SimulationResponse simulateOrder(SimulationOrder simulator) {
        return webclientBuilder.build()
                .post()
                .uri("http://coupon-calculate-serv/calculator/simulate")
                .bodyValue(simulator)
                .retrieve()
                .bodyToMono(SimulationResponse.class)
                .block();
    }
}
