package com.xdsty.orderservice.service;

import basecommon.util.PageUtil;
import basecommon.util.PriceCalculateUtil;
import com.google.common.collect.Lists;
import com.xdsty.orderclient.dto.OrderIdDto;
import com.xdsty.orderclient.dto.OrderListQueryDto;
import com.xdsty.orderclient.dto.OrderModuleDto;
import com.xdsty.orderclient.dto.OrderValidDto;
import com.xdsty.orderclient.enums.OrderModuleEnum;
import com.xdsty.orderclient.enums.OrderStatusEnum;
import com.xdsty.orderclient.enums.OrderValidEnum;
import com.xdsty.orderclient.re.OrderListProductRe;
import com.xdsty.orderclient.re.OrderListRe;
import com.xdsty.orderclient.re.OrderModuleRe;
import com.xdsty.orderclient.re.OrderPayPageRe;
import com.xdsty.orderclient.re.OrderValidRe;
import com.xdsty.orderclient.service.OrderService;
import com.xdsty.orderservice.entity.Order;
import com.xdsty.orderservice.entity.OrderAdditional;
import com.xdsty.orderservice.entity.OrderProduct;
import com.xdsty.orderservice.entity.OrderQuery;
import com.xdsty.orderservice.mapper.OrderAdditionalMapper;
import com.xdsty.orderservice.mapper.OrderMapper;
import com.xdsty.orderservice.mapper.OrderProductMapper;
import javax.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@DubboService(version = "1.0")
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderProductMapper orderProductMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderAdditionalMapper orderAdditionalMapper;

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

    @Override
    public List<OrderListRe> getOrderList(OrderListQueryDto dto) {
        // 获取查询参数
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setUserId(dto.getUserId());
        orderQuery.setStatus(dto.getStatus());
        PageUtil.initPageInfo(orderQuery, dto);

        // 获取对应订单
        List<Order> orders = orderMapper.getOrderPage(orderQuery);
        if(CollectionUtils.isEmpty(orders)) {
            return Lists.newArrayList();
        }
        List<OrderListRe> res = new ArrayList<>(orders.size());

        // 根据订单获取订单中商品和商品价格
        List<Long> orderIds = orders.stream().map(Order::getOrderId).collect(Collectors.toList());
        List<OrderProduct> orderProducts = orderProductMapper.getOrderProductByOrderSet(orderIds);

        // 根据订单id获取订单商品的附加
        List<OrderAdditional> orderAdditionals = orderAdditionalMapper.getOrderAdditionalByOrderSet(orderIds);
        // orderProductId --> additionalIds
        Map<Long, List<OrderAdditional>> additionalMap = new HashMap<>(8);
        if(!CollectionUtils.isEmpty(orderAdditionals)) {
            additionalMap = orderAdditionals.stream().collect(Collectors.groupingBy(OrderAdditional::getOrderProductId, Collectors.toList()));
        }

        // 组合订单和订单商品以及商品的附加
        Map<Long, List<OrderProduct>> orderProductMap = orderProducts.stream()
                .collect(Collectors.groupingBy(OrderProduct::getOrderId, Collectors.toList()));
        for(Order order : orders) {
            OrderListRe re = new OrderListRe();
            re.setOrderId(order.getOrderId());
            re.setStatus(order.getStatus());
            re.setTotalPrice(order.getTotalPrice());
            // 订单下的商品
            Map<Long, List<OrderAdditional>> finalAdditionalMap = additionalMap;
            List<OrderListProductRe> productRes = orderProductMap.get(order.getOrderId()).stream().map(e -> {
                OrderListProductRe productRe = new OrderListProductRe();
                productRe.setTotalPrice(e.getTotalPrice());
                productRe.setNum(e.getProductNum());
                productRe.setProductId(e.getProductId());
                // 获取商品的附加
                List<OrderAdditional> additionals = finalAdditionalMap.get(e.getId());
                if(!CollectionUtils.isEmpty(additionals)) {
                    productRe.setAdditionalIds(additionals.stream().map(OrderAdditional::getAdditionalId).collect(Collectors.toList()));
                }
                return productRe;
            }).collect(Collectors.toList());
            re.setProducts(productRes);
            res.add(re);
        }
        return res;
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
        BigDecimal totalPrice = new BigDecimal("0");
        // 计算商品的价格
        for (OrderProduct orderProduct : orderProducts) {
            totalPrice = PriceCalculateUtil.add(totalPrice, PriceCalculateUtil.multiply(orderProduct.getTotalPrice(), orderProduct.getProductNum()));
        }
        return totalPrice;
    }

}
