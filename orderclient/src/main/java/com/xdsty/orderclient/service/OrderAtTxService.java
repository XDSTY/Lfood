package com.xdsty.orderclient.service;

import com.xdsty.orderclient.dto.OrderAddDto;

/**
 * @author 张富华
 * @date 2020/8/12 18:00
 */
public interface OrderAtTxService {

    /**
     * 添加订单
     * @param orderAddDto
     */
    long placeOrder(OrderAddDto orderAddDto);

}
