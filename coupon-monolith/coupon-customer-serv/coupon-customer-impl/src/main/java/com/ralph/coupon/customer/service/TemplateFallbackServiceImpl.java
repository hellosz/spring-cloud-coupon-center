package com.ralph.coupon.customer.service;

import com.ralph.coupon.customer.feign.TemplateService;
import com.ralph.coupon.template.api.beans.CouponTemplateInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * @ClassName: TemplateFallbackServiceImpl
 * @description:
 * @author: Neng.Tian
 * @create: 2024-10-09 12:04
 **/
@Slf4j
@Service
public class TemplateFallbackServiceImpl implements TemplateService {

    /**
     * 查询模板
     *
     * @param msg
     * @return
     */
    @Override
    public String retrieveTemplate(String msg) {
        return null;
    }

    /**
     * 获取模板信息
     *
     * @param templateId
     * @return
     */
    @Override
    public CouponTemplateInfo getTemplate(Long templateId) {
        return null;
    }

    /**
     * 批量获取 Coupon 信息
     *
     * @param ids
     * @return
     */
    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids) {
        return null;
    }

    @Override
    public boolean timeout(Integer timeout) {
        log.warn("TemplateFallbackServiceImpl timout fallback logic");

        return false;
    }

    @Override
    public boolean randomBreak(Integer factor) {
        log.warn("TemplateFallbackServiceImpl random break fallback logic");

        return false;
    }
}
