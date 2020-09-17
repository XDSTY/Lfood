package com.xdsty.api.controller.content.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderListInfoContent {

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单中商品
     */
    private List<OrderListProductContent> products;
}
