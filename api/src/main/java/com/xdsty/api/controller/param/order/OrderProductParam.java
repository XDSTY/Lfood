package com.xdsty.api.controller.param.order;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderProductParam {

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品数量
     */
    private Integer productNum;

    /**
     * 商品单价
     */
    private BigDecimal productPrice;

    /**
     * 商品附加项
     */
    private List<OrderProductAdditionalParam> items;

}
