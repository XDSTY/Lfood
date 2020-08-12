package com.xdsty.orderservice.service;

import com.xdsty.orderclient.dto.OrderAddDto;
import com.xdsty.orderclient.service.OrderAtTxService;
import com.xdsty.orderservice.mapper.OrderMapper;
import com.xdsty.orderservice.mapper.OrderProductMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * @author 张富华
 * @date 2020/8/12 18:27
 */
@Service
@DubboService(version = "1.0")
public class OrderAtTxServiceImpl implements OrderAtTxService {

    private OrderMapper orderMapper;

    private OrderProductMapper orderProductMapper;

    public OrderAtTxServiceImpl(OrderMapper orderMapper, OrderProductMapper orderProductMapper) {
        this.orderMapper = orderMapper;
        this.orderProductMapper = orderProductMapper;
    }

    @Override
    public long placeOrder(OrderAddDto orderAddDto) {
        return 0;
    }
}
