package com.xdsty.orderclient.service;


import com.xdsty.orderclient.dto.OrderAmountCheckDto;

/**
 * @author 张富华
 * @date 2020/9/4 10:00
 */
public interface OrderProductService {

    /**
     * 校验订单金额和传入的是否相同
     * @param dto
     * @return
     */
    boolean checkOrderAmount(OrderAmountCheckDto dto);

}
