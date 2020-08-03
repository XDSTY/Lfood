package com.xdsty.txclient.service;

import com.xdsty.orderclient.dto.OrderAddDto;

public interface OrderTransactionService {

    /**
     * 下单接口
     * @param dto
     * @return
     */
    Long placeOrder(OrderAddDto dto);

}
