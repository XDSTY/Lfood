package com.xdsty.orderbackservice.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRollbackProduct {

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品数量
     */
    private Integer productNum;

}
