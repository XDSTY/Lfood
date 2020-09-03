package com.xdsty.payclient.service;

import com.xdsty.payclient.dto.PayDto;

/**
 * @author 张富华
 * @date 2020/8/17 9:36
 */
public interface PayTxService {

    /**
     * 模拟付款接口
     * @param payDto 订单信息
     */
    void pay(PayDto payDto);

}
