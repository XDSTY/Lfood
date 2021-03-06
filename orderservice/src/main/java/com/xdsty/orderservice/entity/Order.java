package com.xdsty.orderservice.entity;

import lombok.ToString;
import java.math.BigDecimal;
import java.util.Date;

@ToString
public class Order {

    private Long orderId;

    private Long userId;

    private BigDecimal totalPrice;

    private Integer status;

    private Date createTime;

    private Date payEndTime;

    private Long uniqueRow;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayEndTime() {
        return payEndTime;
    }

    public void setPayEndTime(Date payEndTime) {
        this.payEndTime = payEndTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUniqueRow() {
        return uniqueRow;
    }

    public void setUniqueRow(Long uniqueRow) {
        this.uniqueRow = uniqueRow;
    }
}
