package com.xdsty.orderservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderAdditional {

    private Long id;

    /**
     * 订单商品项id
     */
    private Long orderProductId;

    /**
     * 商品项附加
     */
    private Long additionalId;

    private Integer num;

    private BigDecimal price;

    private Long orderId;

}
