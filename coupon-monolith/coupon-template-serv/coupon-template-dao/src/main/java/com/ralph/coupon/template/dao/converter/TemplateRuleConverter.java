package com.ralph.coupon.template.dao.converter;

import com.alibaba.fastjson.JSON;
import com.ralph.coupon.template.api.beans.rules.TemplateRule;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @ClassName: TemplateRuleConverter
 * @description:
 * @author: Neng.Tian
 * @create: 2024-08-30 00:30
 **/
@Converter
public class TemplateRuleConverter implements AttributeConverter<TemplateRule, String> {


    @Override
    public String convertToDatabaseColumn(TemplateRule templateRule) {
        return JSON.toJSONString(templateRule);
    }

    @Override
    public TemplateRule convertToEntityAttribute(String rule) {
        return JSON.parseObject(rule, TemplateRule.class);
    }
}
