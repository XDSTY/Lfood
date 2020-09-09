package com.xdsty.orderservice.service;

import com.xdsty.orderclient.service.OrderService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@DubboService(version = "1.0")
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public BigDecimal getOrderTotalPrice(Long orderId) {
        return null;
    }
}
