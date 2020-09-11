package com.xdsty.payclient.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 张富华
 * @date 2020/8/17 9:37
 */
public class PayDto implements Serializable {

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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
