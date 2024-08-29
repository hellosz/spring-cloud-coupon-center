package com.ralph.coupon.template.api.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: TemplateSearchParams
 * @description:
 * @author: Neng.Tian
 * @create: 2024-08-29 23:56
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateSearchParams {
    private Long id;
    private String name;
    private String type;
    private Long shopId;
    private Boolean available;
    private int page;
    private int pageSize;
}