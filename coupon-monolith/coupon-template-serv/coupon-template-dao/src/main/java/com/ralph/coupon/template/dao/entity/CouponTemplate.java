package com.ralph.coupon.template.dao.entity;

import com.ralph.coupon.template.api.beans.rules.TemplateRule;
import com.ralph.coupon.template.api.enums.CouponType;
import com.ralph.coupon.template.dao.converter.CouponTypeConverter;
import com.ralph.coupon.template.dao.converter.TemplateRuleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: CouponTemplate
 * @description:
 * @author: Neng.Tian
 * @create: 2024-08-30 00:04
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon_template")
public class CouponTemplate implements Serializable {
    private static final long serialVersionUID = -7710589957740233004L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "available", nullable = false)
    private Boolean available;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "type", nullable = false)
    @Convert(converter = CouponTypeConverter.class)
    private CouponType category;

    @CreatedDate
    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @Column(name = "rule", nullable = false)
    @Convert(converter = TemplateRuleConverter.class)
    private TemplateRule rule;
}