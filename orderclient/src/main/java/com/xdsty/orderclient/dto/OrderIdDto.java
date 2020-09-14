package com.xdsty.orderclient.dto;

import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/9/14 16:06
 */
public class OrderIdDto implements Serializable {

    private Long orderId;

    private Long userId;

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
}
