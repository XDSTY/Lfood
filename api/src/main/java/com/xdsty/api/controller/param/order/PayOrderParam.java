package com.xdsty.api.controller.param.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PayOrderParam {

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单总价
     */
    private BigDecimal totalPrice;

    /**
     * 支付途径
     */
    private Integer payChannel;

    /**
     * 付款类型
     */
    private Integer payType;

}
