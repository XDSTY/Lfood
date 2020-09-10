package com.xdsty.api.controller;

import basecommon.exception.BusinessRuntimeException;
import basecommon.util.PriceCalculateUtil;
import com.xdsty.api.config.annotation.PackageResult;
import com.xdsty.api.controller.param.OrderAddParam;
import com.xdsty.api.controller.param.OrderProductAddParam;
import com.xdsty.api.controller.param.order.PayOrderParam;
import com.xdsty.api.util.SessionUtil;
import com.xdsty.orderclient.dto.OrderAddDto;
import com.xdsty.orderclient.dto.OrderProductAddDto;
import com.xdsty.orderclient.service.OrderService;
import com.xdsty.txclient.service.OrderAtTransactionService;
import com.xdsty.txclient.service.OrderTransactionService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.web.bind.annotation.PostMapping;
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
    private OrderAtTransactionService orderAtTransactionService;

    @DubboReference(version = "1.0", retries = 0)
    private OrderService orderService;

    @PostMapping("placeOrder")
    public void placeOrder() {
        OrderAddParam param = new OrderAddParam();
        param.setTotalPrice(BigDecimal.valueOf(10.0));
        param.setUserId(1L);

        List<OrderProductAddParam> orderProductAddParams = new ArrayList<>();
        OrderProductAddParam productAddParam = new OrderProductAddParam();
        productAddParam.setProductId(1L);
        productAddParam.setProductNum(1);
        productAddParam.setProductPrice(BigDecimal.valueOf(10.0));
        orderProductAddParams.add(productAddParam);
        param.setOrderProductAdds(orderProductAddParams);

        OrderAddDto dto = convert2OrderAddDto(param);

        orderTransactionService.placeOrder(dto);
    }

    @PostMapping("placeOrderWithAtMode")
    public void placeOrderWithAtMode() {
        OrderAddParam param = new OrderAddParam();
        param.setTotalPrice(BigDecimal.valueOf(10.0));
        param.setUserId(1L);

        List<OrderProductAddParam> orderProductAddParams = new ArrayList<>();
        OrderProductAddParam productAddParam = new OrderProductAddParam();
        productAddParam.setProductId(1L);
        productAddParam.setProductNum(1);
        productAddParam.setProductPrice(BigDecimal.valueOf(10.0));
        orderProductAddParams.add(productAddParam);
        param.setOrderProductAdds(orderProductAddParams);

        OrderAddDto dto = convert2OrderAddDto(param);
        orderAtTransactionService.placeOrder(dto);
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

        // 开启付款分布式事务

    }

    private OrderAddDto convert2OrderAddDto(OrderAddParam param) {
        OrderAddDto dto = new OrderAddDto();
        dto.setUserId(param.getUserId());
        dto.setTotalPrice(param.getTotalPrice());
        dto.setProductDtos(param.getOrderProductAdds().stream().map(this::convert2OrderProductAddDto).collect(Collectors.toList()));
        return dto;
    }

    private OrderProductAddDto convert2OrderProductAddDto(OrderProductAddParam param) {
        OrderProductAddDto dto = new OrderProductAddDto();
        dto.setProductId(param.getProductId());
        dto.setProductNum(param.getProductNum());
        dto.setProductPrice(param.getProductPrice());
        return dto;
    }

}
