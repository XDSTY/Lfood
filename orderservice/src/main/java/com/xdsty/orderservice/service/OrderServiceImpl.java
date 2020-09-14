package com.xdsty.orderservice.service;

import basecommon.util.PriceCalculateUtil;
import com.xdsty.orderclient.dto.OrderIdDto;
import com.xdsty.orderclient.re.OrderPayPageRe;
import com.xdsty.orderclient.service.OrderService;
import com.xdsty.orderservice.entity.Order;
import com.xdsty.orderservice.entity.OrderAdditional;
import com.xdsty.orderservice.entity.OrderProduct;
import com.xdsty.orderservice.entity.enums.OrderStatusEnum;
import com.xdsty.orderservice.mapper.OrderAdditionalMapper;
import com.xdsty.orderservice.mapper.OrderMapper;
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

    private OrderMapper orderMapper;

    public OrderServiceImpl(OrderProductMapper orderProductMapper, OrderAdditionalMapper orderAdditionalMapper, OrderMapper orderMapper) {
        this.orderProductMapper = orderProductMapper;
        this.orderAdditionalMapper = orderAdditionalMapper;
        this.orderMapper = orderMapper;
    }

    @Override
    public BigDecimal getOrderTotalPrice(Long orderId) {
        // 获取订单的商品和附加项价格信息
        List<OrderProduct> orderProducts = orderProductMapper.getOrderProductList(orderId);
        List<OrderAdditional> orderAdditionals = orderAdditionalMapper.getOrderAdditionals(orderId);
        BigDecimal totalPrice = new BigDecimal("0");
        // 计算商品的价格
        for (OrderProduct orderProduct : orderProducts) {
            totalPrice = PriceCalculateUtil.add(totalPrice, PriceCalculateUtil.multiply(orderProduct.getProductPrice(), orderProduct.getProductNum()));
        }
        if(!CollectionUtils.isEmpty(orderAdditionals)) {
            for (OrderAdditional orderAdditional : orderAdditionals) {
                // 单个商品下的附加项价格
                BigDecimal orderAddItemPrice = PriceCalculateUtil.multiply(orderAdditional.getNum(), orderAdditional.getPrice());
                // 计算多个商品附加项价格
                Integer productNum = getProductNum(orderProducts, orderAdditional.getNum());
                orderAddItemPrice = PriceCalculateUtil.multiply(productNum, orderAddItemPrice);
                totalPrice = PriceCalculateUtil.add(totalPrice, orderAddItemPrice);
            }
        }
        return totalPrice;
    }

    private Integer getProductNum(List<OrderProduct> orderProducts, int orderProductId) {
        OrderProduct orderProduct = orderProducts.stream().filter(o -> orderProductId == o.getId()).findFirst().orElse(null);
        if(orderProduct != null) {
            return orderProduct.getProductNum();
        }
        return null;
    }

    @Override
    public OrderPayPageRe getOrderPayInfo(OrderIdDto dto) {
        Order order = orderMapper.getOrder(dto.getUserId(), dto.getOrderId());

        OrderPayPageRe re = new OrderPayPageRe();
        re.setShouldPayPrice(order.getTotalPrice());
        re.setCreateTime(order.getCreateTime());
        re.setEndTime(order.getPayEndTime());
        re.setStatus(order.getStatus());
        return re;
    }
}
