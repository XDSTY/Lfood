package com.xdsty.api.controller.param.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderProductAdditionalParam {

    /**
     * 商品附加项id
     */
    private Long id;

    /**
     * 商品附加项数量
     */
    private Integer num;

    /**
     * 商品附加项价格
     */
    private BigDecimal price;

}
