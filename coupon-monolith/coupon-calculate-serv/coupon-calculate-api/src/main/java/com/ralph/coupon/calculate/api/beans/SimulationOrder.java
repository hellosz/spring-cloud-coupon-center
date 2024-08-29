package com.ralph.coupon.calculate.api.beans;

import com.ralph.coupon.template.api.beans.CouponInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName: SimulationOrder
 * @description:
 * @author: Neng.Tian
 * @create: 2024-09-03 22:34
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimulationOrder {

    @NotEmpty
    private List<Product> products;

    @NotEmpty
    private List<Long> couponIDs;

    private List<CouponInfo> couponInfos;

    @NotNull
    private Long userId;
}
