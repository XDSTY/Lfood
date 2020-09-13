package com.xdsty.api.service;

import basecommon.exception.BusinessRuntimeException;
import basecommon.util.PriceCalculateUtil;
import com.xdsty.api.controller.param.order.OrderAddParam;
import com.xdsty.api.controller.param.order.OrderProductAdditionalParam;
import com.xdsty.api.controller.param.order.OrderProductParam;
import com.xdsty.productclient.dto.OrderProductAdditionalDto;
import com.xdsty.productclient.dto.OrderProductDto;
import com.xdsty.productclient.service.ProductService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderCheckService {

    @DubboReference(version = "1.0", timeout = 3000)
    private ProductService productService;

    /**
     * 1、校验订单总价是否正确
     * 2、校验订单中商品价格是否变化
     * @param param
     * @return
     */
    public void checkOrderProductValid(OrderAddParam param) {
        // 校验订单总价
        if(!checkOrderPrice(param)) {
            throw new BusinessRuntimeException("订单价格异常，请刷新重试");
        }
        // 商品服务校验商品和附加价格
        List<OrderProductDto> orderProductDtos =  param.getOrderProductAdds().stream().map(this::convert2OrderProductDto).collect(Collectors.toList());
        productService.checkOrderProduct(orderProductDtos);
    }

    private OrderProductDto convert2OrderProductDto(OrderProductParam param) {
        OrderProductDto dto = new OrderProductDto();
        dto.setProductId(param.getProductId());
        dto.setPrice(param.getProductPrice());
        if(!CollectionUtils.isEmpty(param.getItems())) {
            dto.setOrderProductAdditionals(param.getItems().stream().map(this::convert2OrderProductAdditionalDto).collect(Collectors.toList()));
        }
        return dto;
    }

    private OrderProductAdditionalDto convert2OrderProductAdditionalDto(OrderProductAdditionalParam param) {
        OrderProductAdditionalDto dto = new OrderProductAdditionalDto();
        dto.setAdditionalId(param.getId());
        dto.setPrice(param.getPrice());
        return dto;
    }

    private boolean checkOrderPrice(OrderAddParam param) {
        BigDecimal orderPrice = new BigDecimal(0);
        // 计算订单中商品的价格
        for(OrderProductParam product : param.getOrderProductAdds()) {
            orderPrice = PriceCalculateUtil.add(orderPrice, PriceCalculateUtil.multiply(product.getProductPrice(), product.getProductNum()));
            // 商品的附加不为空，则加上附加的价格
            if(!CollectionUtils.isEmpty(product.getItems())) {
                for(OrderProductAdditionalParam additional : product.getItems()) {
                    orderPrice = PriceCalculateUtil.add(orderPrice, PriceCalculateUtil.multiply(product.getProductNum(), PriceCalculateUtil.multiply(additional.getNum(), additional.getPrice())));
                }
            }
        }
        return PriceCalculateUtil.equals(param.getTotalPrice(), orderPrice);
    }

}
