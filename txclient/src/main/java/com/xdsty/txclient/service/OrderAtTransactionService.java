package com.xdsty.txclient.service;

import com.xdsty.orderclient.dto.OrderAddDto;

public interface OrderAtTransactionService {

    /**
     * at事务入口
     * @param dto
     * @return
     */
    long placeOrder(OrderAddDto dto);

}
