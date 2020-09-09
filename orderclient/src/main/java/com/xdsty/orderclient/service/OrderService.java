package com.xdsty.orderclient.service;

import java.math.BigDecimal;

/**
 * @author 张富华
 * @date 2020/7/30 9:23
 */
public interface OrderService {

    /**
     * 获取订单总价
     * @param orderId
     * @return
     */
    BigDecimal getOrderTotalPrice(Long orderId);

}
