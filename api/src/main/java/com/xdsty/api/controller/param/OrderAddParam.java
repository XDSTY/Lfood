package com.xdsty.api.controller.param;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderAddParam {

    private Long userId;

    private BigDecimal totalPrice;

    private List<OrderProductAddParam> orderProductAdds;

}
