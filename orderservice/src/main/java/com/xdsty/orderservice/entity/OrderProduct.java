package com.xdsty.orderservice.entity;

import java.math.BigDecimal;
import java.util.List;

public class OrderProduct {

    private Long id;

    private Long orderId;

    private Long productId;

    private Integer productNum;

    private BigDecimal productPrice;

    private List<OrderAdditional> orderAdditionals;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public List<OrderAdditional> getOrderAdditionals() {
        return orderAdditionals;
    }

    public void setOrderAdditionals(List<OrderAdditional> orderAdditionals) {
        this.orderAdditionals = orderAdditionals;
    }
}
