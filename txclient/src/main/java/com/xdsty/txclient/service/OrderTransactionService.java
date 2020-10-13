package com.xdsty.txclient.service;

import com.xdsty.orderclient.dto.OrderAddDto;
import com.xdsty.orderclient.re.OrderAddRe;

public interface OrderTransactionService {

    /**
     * 下单接口
     * @param dto
     * @return
     */
    OrderAddRe placeOrder(OrderAddDto dto);

}
