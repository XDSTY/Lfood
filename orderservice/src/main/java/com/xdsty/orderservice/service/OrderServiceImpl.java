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
import java.util.Objects;

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
        Long userId = dto.getUserId();
        // 获取代付款和待配送module
        List<OrderModuleRe> orderModuleRes = getOrderWaitPayAndDeliveryModule(userId);
        // 获取已完成和退款module
        orderModuleRes.addAll(getFinishAndRefundModule());
        return orderModuleRes;
    }

    /**
     * 获取代付款和待配送module
     * @param userId
     * @return
     */
    private List<OrderModuleRe> getOrderWaitPayAndDeliveryModule(Long userId) {
        List<OrderModuleRe> res = new ArrayList<>(4);
        // 获取代付款和代配送的订单
        List<Order> orders = orderMapper.getOrderListByUserAndStatus(userId, Constant.ORDER_MODULE_STATUS);
        int waitPay = 0, waitDelivery = 0;
        if(!CollectionUtils.isEmpty(orders)) {
            // 计算等待付款和待配送的有多少
            for(Order o : orders) {
                if(o.getStatus().equals(OrderStatusEnum.WAIT_PAY.getStatus())) {
                    waitPay ++;
                } else {
                    waitDelivery ++;
                }
            }
        }
        // 代付款module
        OrderModuleRe waitPayModule = new OrderModuleRe();
        waitPayModule.setModuleType(OrderModuleEnum.WAIT_PAY.getStatus());
        waitPayModule.setNum(waitPay > 0 ? waitPay : null);
        waitPayModule.setStatus(OrderStatusEnum.WAIT_PAY.getStatus());
        res.add(waitPayModule);

        // 代配送module
        OrderModuleRe waitDeliveryModule = new OrderModuleRe();
        waitDeliveryModule.setModuleType(OrderModuleEnum.WAIT_DELIVER.getStatus());
        waitDeliveryModule.setNum(waitDelivery > 0 ? waitDelivery : null);
        waitDeliveryModule.setStatus(OrderStatusEnum.SUCCESS.getStatus());
        res.add(waitDeliveryModule);
        return res;
    }

    private List<OrderModuleRe> getFinishAndRefundModule() {
        List<OrderModuleRe> res = new ArrayList<>(2);
        // 已完成module
        OrderModuleRe finishModule = new OrderModuleRe();
        finishModule.setModuleType(OrderModuleEnum.FINISH.getStatus());
        finishModule.setStatus(OrderStatusEnum.FINISH.getStatus());
        res.add(finishModule);

        // 退款module
        OrderModuleRe refundModule = new OrderModuleRe();
        refundModule.setModuleType(OrderModuleEnum.REFUND.getStatus());
        refundModule.setStatus(OrderStatusEnum.REFUND.getStatus());
        res.add(refundModule);
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
