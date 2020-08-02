package com.xdsty.algorderclient.service;

import com.xdsty.algorderclient.dto.OrderAddDto;

/**
 * @author 张富华
 * @date 2020/7/30 9:23
 */
public interface OrderService {

    void placeOrder(OrderAddDto dto);

}
