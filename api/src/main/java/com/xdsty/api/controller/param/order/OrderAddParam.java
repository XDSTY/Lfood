package com.xdsty.api.controller.param.order;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderAddParam {

    /**
     * 订单总价
     */
    private BigDecimal totalPrice;

    /**
     * 订单中商品
     */
    private List<OrderProductParam> orderProductAdds;

}
