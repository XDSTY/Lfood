package com.xdsty.api.controller.content.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderListProductRe {

    private Long productId;

    /**
     * 商品名
     */
    private String productName;

    /**
     * 商品价格（包括附加项价格）
     */
    private BigDecimal totalPrice;

}
