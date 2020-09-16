package com.xdsty.orderservice.service;

import basecommon.util.PriceCalculateUtil;
import com.xdsty.orderclient.dto.OrderIdDto;
import com.xdsty.orderclient.dto.OrderModuleDto;
import com.xdsty.orderclient.dto.OrderValidDto;
import com.xdsty.orderclient.enums.OrderModuleEnum;
import com.xdsty.orderclient.enums.OrderStatusEnum;
import com.xdsty.orderclient.enums.OrderValidEnum;
import com.xdsty.orderclient.re.OrderModuleRe;
import com.xdsty.orderclient.re.OrderPayPageRe;
import com.xdsty.orderclient.re.OrderValidRe;
import com.xdsty.orderclient.service.OrderService;
import com.xdsty.orderservice.common.Constant;
import com.xdsty.orderservice.entity.Order;
import com.xdsty.orderservice.entity.OrderAdditional;
import com.xdsty.orderservice.entity.OrderProduct;
import com.xdsty.orderservice.mapper.OrderAdditionalMapper;
import com.xdsty.orderservice.mapper.OrderMapper;
import com.xdsty.orderservice.mapper.OrderProductMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public OrderPayPageRe getOrderPayInfo(OrderIdDto dto) {
        Order order = orderMapper.getOrder(dto.getUserId(), dto.getOrderId());

        OrderPayPageRe re = new OrderPayPageRe();
        re.setShouldPayPrice(order.getTotalPrice());
        re.setCreateTime(order.getCreateTime());
        re.setEndTime(order.getPayEndTime());
        re.setStatus(order.getStatus());
        return re;
    }

    @Override
    public OrderValidRe checkOrderValid(OrderValidDto dto) {
        Order order = orderMapper.getOrder(dto.getUserId(), dto.getOrderId());
        OrderValidRe re = new OrderValidRe();
        // 校验订单是否存在
        if(Objects.isNull(order)) {
            re.setCode(OrderValidEnum.NO_EXIST_ORDER.getCode());
            re.setMsg(OrderValidEnum.NO_EXIST_ORDER.getDesc());
            return re;
        }
        // 校验订单是否已超时未付款取消
        if(OrderStatusEnum.ORDER_OVERTIME.getStatus().equals(order.getStatus())) {
            re.setCode(OrderValidEnum.PAY_OVERTIME.getCode());
            re.setMsg(OrderValidEnum.PAY_OVERTIME.getDesc());
            return re;
        }
        // 订单是否手动取消
        if(OrderStatusEnum.CANCELED.getStatus().equals(order.getStatus())) {
            re.setCode(OrderValidEnum.ORDER_CANCAL.getCode());
            re.setMsg(OrderValidEnum.ORDER_CANCAL.getDesc());
            return re;
        }
        // 订单是否未付款
        if(!OrderStatusEnum.WAIT_PAY.getStatus().equals(order.getStatus())) {
            re.setCode(OrderValidEnum.ORDER_CHANGED.getCode());
            re.setMsg(OrderValidEnum.ORDER_CHANGED.getDesc());
            return re;
        }
        // 校验订单价格
        if(!PriceCalculateUtil.equals(dto.getTotalPrice(), getOrderTotalPrice(dto.getOrderId()))) {
            re.setCode(OrderValidEnum.PRICE_MODIFY.getCode());
            re.setMsg(OrderValidEnum.PRICE_MODIFY.getDesc());
            return re;
        }
        return re;
    }

    @Override
    public List<OrderModuleRe> getOrderModules(OrderModuleDto dto) {
        // 根据moduleType获取需要查询的订单状态
        List<OrderModuleRe> moduleRes = initByModuleType(dto.getModuleType());
        // 将模块分成status -> module，方便统计各个订单数量
        Map<Integer, OrderModuleRe> moduleReMap = moduleRes.stream().collect(Collectors.toMap(OrderModuleRe::getStatus, e -> e));
        // 获取需要查询的订单状态
        List<Integer> statusList = moduleRes.stream().map(OrderModuleRe::getStatus).collect(Collectors.toList());
        List<Order> orders = orderMapper.getOrderListByUserAndStatus(dto.getUserId(), statusList);
        // 统计各个状态订单的数量
        calculateOrderNum(orders, moduleReMap);
        return moduleRes;
    }

    /**
     * 统计各个状态订单的数量
     * @param orders
     * @param moduleReMap
     */
    private void calculateOrderNum(List<Order> orders, Map<Integer, OrderModuleRe> moduleReMap) {
        for(Order order : orders) {
            OrderModuleRe re = moduleReMap.get(order.getStatus());
            re.setNum(re.getNum() + 1);
        }
    }

    /**
     * 从枚举中获取各个模块
     * @param moduleTypes
     * @return
     */
    private List<OrderModuleRe> initByModuleType(List<Integer> moduleTypes) {
        List<OrderModuleRe> res = new ArrayList<>(moduleTypes.size());
        moduleTypes.forEach(e -> {
            OrderModuleEnum moduleEnum = OrderModuleEnum.getEnumByType(e);
            OrderModuleRe re = new OrderModuleRe();
            re.setModuleType(e);
            re.setStatus(moduleEnum.getStatus());
            re.setNum(0);
            res.add(re);
        });
        return res;
    }

    private BigDecimal getOrderTotalPrice(Long orderId) {
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
                Integer productNum = getProductNum(orderProducts, orderAdditional.getOrderProductId());
                orderAddItemPrice = PriceCalculateUtil.multiply(productNum, orderAddItemPrice);
                totalPrice = PriceCalculateUtil.add(totalPrice, orderAddItemPrice);
            }
        }
        return totalPrice;
    }

    private Integer getProductNum(List<OrderProduct> orderProducts, long orderProductId) {
        OrderProduct orderProduct = orderProducts.stream().filter(o -> orderProductId == o.getId()).findFirst().orElse(null);
        if(orderProduct != null) {
            return orderProduct.getProductNum();
        }
        return null;
    }
}
