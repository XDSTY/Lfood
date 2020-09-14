package com.xdsty.api.controller.content.order;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 张富华
 * @date 2020/9/4 11:06
 */
@Getter
@Setter
public class OrderPlaceContent implements Serializable {

    private Long orderId;

    private BigDecimal totalPrice;

}
