package com.xdsty.api.controller.param;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class OrderProductAddParam {

    private Long productId;

    private Integer productNum;

    private BigDecimal productPrice;
}
