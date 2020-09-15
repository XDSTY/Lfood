package com.xdsty.orderclient.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 张富华
 * @date 2020/9/15 15:02
 */
public class OrderValidDto implements Serializable {

    private Long userId;

    private Long orderId;

    private BigDecimal totalPrice;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
