package com.ralph.coupon.template.impl.service;

import com.ralph.coupon.template.api.beans.CouponTemplateInfo;
import com.ralph.coupon.template.api.beans.PagedCouponTemplateInfo;
import com.ralph.coupon.template.api.beans.TemplateSearchParams;
import com.ralph.coupon.template.api.enums.CouponType;
import com.ralph.coupon.template.dao.CouponTemplateDao;
import com.ralph.coupon.template.dao.entity.CouponTemplate;
import com.ralph.coupon.template.impl.converter.CouponTemplateConverter;
import com.ralph.coupon.template.impl.service.intf.CouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName: CouponTemplateServiceImpl
 * @description:
 * @author: Neng.Tian
 * @create: 2024-08-30 23:34
 **/
@Slf4j
@Service
public class CouponTemplateServiceImpl implements CouponTemplateService {

    /**
     * 店铺最大优惠券数量
     */
    private final static int SHOP_MAX_COUPON_NUM = 100;

    @Resource
    private CouponTemplateDao templateDao;

    /**
     * 创建优惠券模板
     *
     * @param request
     * @return
     */
    @Override
    public CouponTemplateInfo createTemplate(CouponTemplateInfo request) {
        // 验证不能超过店铺可用最大的优惠券数量
        if (request.getShopId() != null) {
            Integer total = templateDao.countByShopIdAndAvailable(request.getShopId(), true);
            if (total != null && total >= SHOP_MAX_COUPON_NUM) {
                String msg = String.format("shopId: %d total coupon exceed %d", request.getShopId(), SHOP_MAX_COUPON_NUM);
                log.error(msg);
                throw new UnsupportedOperationException(msg);
            }
        }


        // 保存 coupon
        CouponTemplate template = CouponTemplate.builder()
                .name(request.getName())
                .shopId(request.getShopId())
                .description(request.getDesc())
                .category(CouponType.convert(request.getType()))
                .rule(request.getRule())
                .available(true)
                .build();
        templateDao.save(template);

        // 返回结果
        return CouponTemplateConverter.convertToCouponTemplateInfo(template);
    }

    /**
     * 复制优惠券模板
     *
     * @param templateId
     * @return
     */
    @Override
    public CouponTemplateInfo cloneTemplate(Long templateId) {
        log.info("cloneTemplate templateId: {}", templateId);

        // 数据查找
        CouponTemplate template = templateDao.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("templateId not exists"));

        // 复制属性
        CouponTemplate newCouponTemplate = new CouponTemplate();
        BeanUtils.copyProperties(template, newCouponTemplate);
        newCouponTemplate.setAvailable(true);
        newCouponTemplate.setId(null);

        // 保存数据
        templateDao.save(newCouponTemplate);

        return CouponTemplateConverter.convertToCouponTemplateInfo(newCouponTemplate);
    }

    /**
     * 查询模板
     *
     * @param request
     * @return
     */
    @Override
    public PagedCouponTemplateInfo search(TemplateSearchParams request) {
        // 构建查询参数
        CouponTemplate template = CouponTemplate.builder()
                .available(request.getAvailable())
                .name(request.getName())
                .shopId(request.getShopId())
                .category(CouponType.convert(request.getType()))
                .build();

        // 分页数据查询
        PageRequest page = PageRequest.of(request.getPage(), request.getPageSize());
        Page<CouponTemplate> list = templateDao.findAll(Example.of(template), page);
        List<CouponTemplateInfo> couponTemplateInfos = list.stream()
                .map(CouponTemplateConverter::convertToCouponTemplateInfo)
                .collect(Collectors.toList());

        // 构建分页返回结果
        return PagedCouponTemplateInfo.builder()
                .page(request.getPage())
                .total(list.getTotalElements())
                .templates(couponTemplateInfos)
                .build();
    }

    /**
     * 通过模板 ID 查询模板
     *
     * @param id
     * @return
     */
    @Override
    public CouponTemplateInfo load(Long id) {
        Optional<CouponTemplate> templateOptional = templateDao.findById(id);

        return templateOptional.map(CouponTemplateConverter::convertToCouponTemplateInfo).orElse(null);
    }

    /**
     * 删除模板
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteTemplate(Long id) {
        int updated = templateDao.makeCouponUnavailable(id);

        if (updated == 0) {
            throw new IllegalArgumentException("Template Not Found: " + id);
        }
    }

    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids) {
        List<CouponTemplate> list = templateDao.findAllById(ids);

        return list.stream()
                .collect(Collectors.toMap(CouponTemplate::getId, CouponTemplateConverter::convertToCouponTemplateInfo, (o1, o2) -> o1));
    }
}
