package com.xdsty.orderservice.service;

import basecommon.exception.BusinessRuntimeException;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.xdsty.orderclient.dto.OrderAddDto;
import com.xdsty.orderclient.dto.OrderPayDto;
import com.xdsty.orderclient.dto.OrderProductAddDto;
import com.xdsty.orderclient.service.OrderAtTxService;
import com.xdsty.orderservice.entity.Order;
import com.xdsty.orderservice.entity.OrderProduct;
import com.xdsty.orderservice.entity.UserIntegral;
import com.xdsty.orderservice.entity.enums.OrderStatusEnum;
import com.xdsty.orderservice.mapper.OrderMapper;
import com.xdsty.orderservice.mapper.OrderProductMapper;
import com.xdsty.orderservice.mapper.UserIntegralRecordMapper;
import com.xdsty.orderservice.util.IdWorker;
import io.seata.core.context.RootContext;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 张富华
 * @date 2020/8/12 18:27
 */
@Service
@DubboService(version = "1.0")
public class OrderAtTxServiceImpl implements OrderAtTxService {

    private static final Logger log = LoggerFactory.getLogger(OrderAtTxServiceImpl.class);

    private OrderMapper orderMapper;

    private OrderProductMapper orderProductMapper;

    private UserIntegralRecordMapper userIntegralRecordMapper;

    public OrderAtTxServiceImpl(OrderMapper orderMapper, OrderProductMapper orderProductMapper, UserIntegralRecordMapper userIntegralRecordMapper) {
        this.orderMapper = orderMapper;
        this.orderProductMapper = orderProductMapper;
        this.userIntegralRecordMapper = userIntegralRecordMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long placeOrder(OrderAddDto dto) {
        log.error("开始订单子事务, 事务id: {}", RootContext.getXID());
        long orderId = IdWorker.getNextId();
        Order order = convert2Order(dto, orderId);
        // 新建未付款的订单
        int count = orderMapper.insertOrder(order);
        if(count <= 0) {
            throw new BusinessRuntimeException("插入订单失败");
        }
        List<OrderProduct> products = dto.getProductDtos().stream().map(e -> convert2OrderProduct(e, orderId)).collect(Collectors.toList());
        count = orderProductMapper.addOrderProduct(products);
        if (count != products.size()) {
            throw new BusinessRuntimeException("新建订单商品信息失败");
        }
        return orderId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(OrderPayDto dto) {
        if(dto.getOrderId() == null || dto.getUserId() == null) {
            throw new BusinessRuntimeException("订单id不能为空");
        }
        // 判断订单状态
        Order selectOrder = orderMapper.getOrder(dto.getUserId(), dto.getOrderId());
        if(!selectOrder.getStatus().equals(OrderStatusEnum.WAIT_PAY.status)) {
            throw new BusinessRuntimeException("重复付款");
        }
        Order order = new Order();
        order.setOrderId(dto.getOrderId());
        order.setStatus(OrderStatusEnum.SUCCESS.status);
        // 修改订单状态
        int count = orderMapper.updateOrder(order);
        if(count < 1) {
            throw new BusinessRuntimeException("修改订单失败");
        }

        //插入积分
        UserIntegral integral = new UserIntegral();
        integral.setType(1);
        integral.setIntegral(dto.getIntegral());
        integral.setUserId(dto.getUserId());
        integral.setOrderId(dto.getOrderId());
        count = userIntegralRecordMapper.insertOne(integral);
        if(count < 1) {
            throw new BusinessRuntimeException("插入积分失败");
        }
    }

    private Order convert2Order(OrderAddDto dto, long orderId) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(dto.getUserId());
        order.setTotalPrice(dto.getTotalPrice());
        order.setStatus(OrderStatusEnum.WAIT_PAY.status);
        return order;
    }

    private OrderProduct convert2OrderProduct(OrderProductAddDto dto, long orderId) {
        OrderProduct product = new OrderProduct();
        product.setOrderId(orderId);
        product.setProductId(dto.getProductId());
        product.setProductNum(dto.getProductNum());
        product.setProductPrice(dto.getProductPrice());
        return product;
    }
}
