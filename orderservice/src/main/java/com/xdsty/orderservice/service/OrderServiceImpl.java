package com.xdsty.orderservice.service;

import com.xdsty.orderclient.service.OrderService;
import com.xdsty.orderservice.entity.OrderAdditional;
import com.xdsty.orderservice.entity.OrderProduct;
import com.xdsty.orderservice.mapper.OrderAdditionalMapper;
import com.xdsty.orderservice.mapper.OrderProductMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@DubboService(version = "1.0")
@Service
public class OrderServiceImpl implements OrderService {

    private OrderProductMapper orderProductMapper;

    private OrderAdditionalMapper orderAdditionalMapper;

    public OrderServiceImpl(OrderProductMapper orderProductMapper, OrderAdditionalMapper orderAdditionalMapper) {
        this.orderProductMapper = orderProductMapper;
        this.orderAdditionalMapper = orderAdditionalMapper;
    }

    @Override
    public BigDecimal getOrderTotalPrice(Long orderId) {
        // 获取订单的商品和附加项价格信息
        List<OrderProduct> orderProducts = orderProductMapper.getOrderProductList(orderId);
        List<OrderAdditional> orderAdditionals = orderAdditionalMapper.getOrderAdditionals(orderId);
        BigDecimal totalPrice = new BigDecimal("0");
        // 计算商品的价格
        if(!CollectionUtils.isEmpty(orderProducts)) {
            orderProducts.stream().forEach(e -> totalPrice.add(e.getProductPrice().multiply(new BigDecimal(e.getProductNum()))));
        }
    }
}
