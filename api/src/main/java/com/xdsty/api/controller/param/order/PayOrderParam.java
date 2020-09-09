package com.xdsty.api.controller.param.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PayOrderParam {

    private Long orderId;

    private BigDecimal totalPrice;

}
