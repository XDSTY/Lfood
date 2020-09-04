package com.xdsty.txservice.service;

import com.xdsty.orderclient.dto.OrderPayDto;
import com.xdsty.orderclient.service.OrderAtTxService;
import com.xdsty.payclient.dto.PayDto;
import com.xdsty.payclient.service.PayTxService;
import com.xdsty.txclient.dto.PayOrderDto;
import com.xdsty.txclient.service.PayOrderTransactionService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0")
public class PayOrderTransactionServiceImpl implements PayOrderTransactionService {

    @DubboReference(version = "1.0")
    private OrderAtTxService orderAtTxService;

    @DubboReference(version = "1.0")
    private PayTxService payTxService;

    @Override
    @GlobalTransactional
    public void payOrder(PayOrderDto dto) {
        // 支付订单，并扣款
        PayDto payDto = new PayDto();
        payDto.setUserId(dto.getUserId());
        payDto.setOrderId(dto.getOrderId());
        payDto.setPayType(dto.getPayType());
        payDto.setTotalAmount(dto.getTotalAmount());
        payDto.setType(dto.getType());
        payTxService.pay(payDto);

        // 修改订单状态
        OrderPayDto orderPayDto = new OrderPayDto();
        orderPayDto.setUserId(dto.getUserId());
        orderPayDto.setIntegral(dto.getIntegral());
        orderPayDto.setOrderId(dto.getOrderId());
        orderAtTxService.payOrder(orderPayDto);
    }
}
