package com.ralph.coupon.customer.feign;

import com.ralph.coupon.template.api.beans.CouponTemplateInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Map;

/**
 * @ClassName: TemplateService
 * @description:
 * @author: Neng.Tian
 * @create: 2024-10-08 23:21
 **/
@FeignClient(path = "/template", name = "coupon-template-serv")
public interface TemplateService {

    /**
     * 查询模板
     * @param msg
     * @return
     */
    @GetMapping("/retrieve")
    String retrieveTemplate(@RequestParam("msg") String msg);

    /**
     * 获取模板信息
     *
     * @param templateId
     * @return
     */
    @GetMapping("/getTemplate")
    CouponTemplateInfo getTemplate(@RequestParam("id") Long templateId);

    /**
     * 批量获取 Coupon 信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/getBatch")
    Map<Long, CouponTemplateInfo> getTemplateInfoMap(@RequestBody Collection<Long> ids);
}

