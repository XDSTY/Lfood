package com.xdsty.orderclient.re;

import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/10/13 17:20
 */
public class OrderAddRe implements Serializable {

    private Integer status;

    private Long orderId;

    public OrderAddRe() {
    }

    public OrderAddRe(Integer status, Long orderId) {
        this.status = status;
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
