package com.ralph.coupon.customer.dao.entity;

import com.ralph.coupon.customer.api.enums.CouponStatus;
import com.ralph.coupon.customer.dao.converter.CouponStatusConverter;
import com.ralph.coupon.template.api.beans.CouponTemplateInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: Coupon
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-15 13:03
 **/
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "template_id", nullable = false)
    private Long templateId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "status", nullable = false)
    @Convert(converter = CouponStatusConverter.class)
    private CouponStatus status;

    // 该属性不会被持久化
    @Transient
    private CouponTemplateInfo templateInfo;

    @CreatedDate
    @Column(name = "created_time", nullable = false)
    private Date createdTime;
}