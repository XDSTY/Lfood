package com.xdsty.orderbackservice.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderRollBackInfo {

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单创建时间
     */
    private long createTime;

    /**
     * 订单结束时间
     */
    private long endTime;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单商品
     */
    private List<OrderRollbackProduct> productList;

}
