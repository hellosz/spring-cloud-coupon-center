package com.ralph.coupon.template.api.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName: PagedCouponTemplateInfo
 * @description:
 * @author: Neng.Tian
 * @create: 2024-08-29 23:13
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagedCouponTemplateInfo {

    public List<CouponTemplateInfo> templates;

    public int page;

    public long total;
}
