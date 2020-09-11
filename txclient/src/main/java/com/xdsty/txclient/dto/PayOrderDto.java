package com.xdsty.txclient.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单付款dto
 */
public class PayOrderDto implements Serializable {

    private Long orderId;

    private BigDecimal totalAmount;

    private Long userId;

    /**
     * 支付方式
     */
    private Integer payChannel;

    /**
     * 支付的类型 下单
     */
    private Integer payType;

    private Integer integral;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }
}
