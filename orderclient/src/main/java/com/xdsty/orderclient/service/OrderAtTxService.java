package com.xdsty.orderclient.service;

import com.xdsty.orderclient.dto.OrderAddDto;
import com.xdsty.orderclient.dto.OrderPayDto;

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

    /**
     * 订单付款，变更订单状态，并添加积分
     * @param dto
     * @return
     */
    void payOrder(OrderPayDto dto);

}
