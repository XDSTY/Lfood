package com.xdsty.orderclient.dto;

import java.io.Serializable;

/**
 * 订单付款
 * @author 张富华
 * @date 2020/9/3 18:11
 */
public class OrderPayDto implements Serializable {

    private Long orderId;

    private Long userId;

    /**
     * 增加的积分
     */
    private Integer integral;

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Long getOrderId() {
        return orderId;
    }


    public Integer getIntegral() {
        return integral;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
