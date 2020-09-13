package com.xdsty.api.controller;

import basecommon.exception.BusinessRuntimeException;
import basecommon.util.PriceCalculateUtil;
import com.xdsty.api.config.annotation.PackageResult;
import com.xdsty.api.controller.param.order.OrderAddParam;
import com.xdsty.api.controller.param.order.OrderProductAdditionalParam;
import com.xdsty.api.controller.param.order.OrderProductParam;
import com.xdsty.api.controller.param.order.PayOrderParam;
import com.xdsty.api.service.IntegralCalculateService;
import com.xdsty.api.service.OrderCheckService;
import com.xdsty.api.util.SessionUtil;
import com.xdsty.orderclient.dto.OrderAddDto;
import com.xdsty.orderclient.dto.OrderProductAddDto;
import com.xdsty.orderclient.dto.OrderProductAdditionalDto;
import com.xdsty.orderclient.service.OrderService;
import com.xdsty.txclient.dto.PayOrderDto;
import com.xdsty.txclient.service.OrderTransactionService;
import com.xdsty.txclient.service.PayOrderTransactionService;
import javax.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@PackageResult
@RequestMapping("order")
@RestController
public class OrderController {

    @DubboReference(version = "1.0", retries = 0)
    private OrderTransactionService orderTransactionService;

    @DubboReference(version = "1.0", retries = 0)
    private OrderService orderService;

    @DubboReference(version = "1.0", retries = 0)
    private PayOrderTransactionService payOrderTransactionService;

    @Resource
    private IntegralCalculateService integralCalculateService;

    @Resource
    private OrderCheckService orderCheckService;

    @PostMapping("placeOrder")
    public void placeOrder(@RequestBody OrderAddParam param) {
        Long userId = SessionUtil.getUserId();
        // 校验商品是否有效和是价格否变化
        orderCheckService.checkOrderProductValid(param);

        // 添加订单
        OrderAddDto dto = convert2OrderAddDto(param);
        dto.setUserId(userId);
        orderTransactionService.placeOrder(dto);
    }

    /**
     * 订单支付页
     */
    @PostMapping("payOrderPage")
    public void payOrderPage() {

    }

    /**
     * 支付订单并添加积分
     * @param param
     */
    @PostMapping("payOrder")
    public void payOrder(PayOrderParam param) {
        if(param.getOrderId() == null || param.getTotalPrice() == null) {
            throw new BusinessRuntimeException("入参错误");
        }
        Long memberId = SessionUtil.getUserId();
        // 从订单获取价格
        BigDecimal orderPrice = orderService.getOrderTotalPrice(param.getOrderId());
        if(!PriceCalculateUtil.equals(orderPrice, param.getTotalPrice())) {
            throw new BusinessRuntimeException("订单价格变化，清重新支付");
        }

        // 计算积分
        int integral = integralCalculateService.calculateIntegral(param.getTotalPrice());

        // 开启付款分布式事务
        PayOrderDto dto = new PayOrderDto();
        dto.setUserId(memberId);
        dto.setOrderId(param.getOrderId());
        dto.setTotalAmount(param.getTotalPrice());
        dto.setPayType(param.getPayType());
        dto.setPayChannel(param.getPayChannel());
        dto.setIntegral(integral);
        payOrderTransactionService.payOrder(dto);
    }

    private OrderAddDto convert2OrderAddDto(OrderAddParam param) {
        OrderAddDto dto = new OrderAddDto();
        dto.setTotalPrice(param.getTotalPrice());
        dto.setProductDtos(param.getOrderProductAdds().stream().map(this::convert2OrderProductAddDto).collect(Collectors.toList()));
        return dto;
    }

    private OrderProductAddDto convert2OrderProductAddDto(OrderProductParam param) {
        OrderProductAddDto dto = new OrderProductAddDto();
        dto.setProductId(param.getProductId());
        dto.setProductNum(param.getProductNum());
        dto.setProductPrice(param.getProductPrice());
        if(!CollectionUtils.isEmpty(param.getItems())) {
            dto.setOrderProductAdditionals(param.getItems().stream().map(this::convert2OrderProductAdditionalDto).collect(Collectors.toList()));
        }
        return dto;
    }

    private OrderProductAdditionalDto convert2OrderProductAdditionalDto(OrderProductAdditionalParam param) {
        OrderProductAdditionalDto dto = new OrderProductAdditionalDto();
        dto.setAdditionalId(param.getId());
        dto.setPrice(param.getPrice());
        dto.setNum(param.getNum());
        return dto;
    }

}
