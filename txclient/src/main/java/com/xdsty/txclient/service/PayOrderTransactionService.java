package com.xdsty.txclient.service;

import com.xdsty.txclient.dto.PayOrderDto;

public interface PayOrderTransactionService {

    /**
     * 支付订单
     * @param dto
     */
    void payOrder(PayOrderDto dto);

}
