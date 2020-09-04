package com.xdsty.orderclient.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 张富华
 * @date 2020/9/4 10:05
 */
public class OrderAmountCheckDto implements Serializable {

    private Long orderId;

    private BigDecimal amount;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
