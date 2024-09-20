package com.ralph.coupon.template.impl.controller;

import com.ralph.coupon.template.api.beans.CouponTemplateInfo;
import com.ralph.coupon.template.api.beans.PagedCouponTemplateInfo;
import com.ralph.coupon.template.api.beans.TemplateSearchParams;
import com.ralph.coupon.template.impl.service.intf.CouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

/**
 * @ClassName: CouponTemplateController
 * @description:
 * @author: Neng.Tian
 * @create: 2024-08-30 23:34
 **/
@RestController
@Slf4j
@RequestMapping("/template")
public class CouponTemplateController {

    @Resource
    private CouponTemplateService templateService;
    @Autowired
    private ResourceUrlProvider mvcResourceUrlProvider;

    @PostMapping("/addTemplate")
    public CouponTemplateInfo addTemplate(@Valid @RequestBody CouponTemplateInfo request) {
        log.info("addTemplate request: {}", request);

        return templateService.createTemplate(request);
    }

    @PostMapping("/cloneTemplate")
    public CouponTemplateInfo cloneTemplate(@RequestParam("id") Long templateId) {
        log.info("cloneTemplate request: {}", templateId);

        return templateService.cloneTemplate(templateId);
    }

    @GetMapping("/getTemplate")
    public CouponTemplateInfo getTemplate(@RequestParam("id") Long id) {
        log.info("getTemplate id: {}", id);

        return templateService.load(id);
    }

    @GetMapping("/getBatch")
    public Map<Long, CouponTemplateInfo> getBatch(@RequestParam("ids") Collection<Long> ids) {
       log.info("getBatch request ids: {}", ids);

       return templateService.getTemplateInfoMap(ids);
    }


    @GetMapping("/search")
    public PagedCouponTemplateInfo search(TemplateSearchParams params) {
       log.info("search request params: {}", params);

       return templateService.search(params);
    }

    @DeleteMapping("/deleteTemplate")
    public void deleteTemplate(@RequestParam("id") Long id) {
        log.info("deleteTemplate id: {}", id);

        templateService.deleteTemplate(id);
    }

    @GetMapping("/retrieve")
    public String retrieve(@RequestParam("msg") String msg) {
        log.info("retrieve msg: {}", msg);

        return msg;
    }
}
