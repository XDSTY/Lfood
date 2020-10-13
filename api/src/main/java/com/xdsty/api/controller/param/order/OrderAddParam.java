package com.xdsty.api.controller.param.order;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderAddParam extends CommonParam {

    /**
     * 订单总价
     */
    private BigDecimal totalPrice;

    /**
     * 前端传来的唯一key
     */
    private Long uniqueRow;

    /**
     * 订单中商品
     */
    private List<OrderProductParam> orderProductAdds;

}
