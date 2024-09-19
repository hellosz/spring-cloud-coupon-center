package com.ralph.coupon.customer.dao;

import com.ralph.coupon.customer.dao.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: CouponDao
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-15 12:48
 **/
public interface CouponDao extends JpaRepository<Coupon, Long> {
    long countByUserIdAndTemplateId(Long userId, Long templateId);
}