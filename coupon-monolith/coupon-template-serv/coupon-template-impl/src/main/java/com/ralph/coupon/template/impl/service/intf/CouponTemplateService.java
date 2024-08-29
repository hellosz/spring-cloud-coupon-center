package com.ralph.coupon.template.impl.service.intf;

import com.ralph.coupon.template.api.beans.CouponTemplateInfo;
import com.ralph.coupon.template.api.beans.PagedCouponTemplateInfo;
import com.ralph.coupon.template.api.beans.TemplateSearchParams;

import java.util.Collection;
import java.util.Map;

/**
 * @ClassName: CouponTemplateService
 * @description:
 * @author: Neng.Tian
 * @create: 2024-08-30 23:35
 **/
public interface CouponTemplateService {

    /**
     * 创建优惠券模板
     *
     * @param request
     * @return
     */
    CouponTemplateInfo createTemplate(CouponTemplateInfo request);

    /**
     * 复制优惠券模板
     *
     * @param templateId
     * @return
     */
    CouponTemplateInfo cloneTemplate(Long templateId);

    /**
     * 查询模板
     *
     * @param request
     * @return
     */
    PagedCouponTemplateInfo search(TemplateSearchParams request);

    /**
     * 通过模板 ID 查询模板
     * @param id
     * @return
     */
    CouponTemplateInfo load(Long id);

    /**
     * 删除模板
     *
     * @param id
     */
    void deleteTemplate(Long id);

    // 批量查询
    Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids);

}
