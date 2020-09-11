package com.xdsty.orderservice.service;

import com.xdsty.orderclient.dto.OrderAmountCheckDto;
import com.xdsty.orderclient.service.OrderProductService;
import com.xdsty.orderservice.mapper.OrderProductMapper;
import com.xdsty.orderservice.util.AmountUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

/**
 * @author 张富华
 * @date 2020/9/4 10:07
 */
@DubboService(version = "1.0")
@Service
public class OrderProductServiceImpl implements OrderProductService {

    private OrderProductMapper orderProductMapper;

    @Autowired
    public void setOrderProductMapper(OrderProductMapper orderProductMapper) {
        this.orderProductMapper = orderProductMapper;
    }

    @Override
    public boolean checkOrderAmount(OrderAmountCheckDto dto) {
        // 从数据库获取订单金额
        return true;
    }
}
