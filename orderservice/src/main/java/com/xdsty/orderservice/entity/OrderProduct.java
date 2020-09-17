package com.xdsty.orderservice.entity;

import java.math.BigDecimal;
import java.util.List;

public class OrderProduct {

    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品数量
     */
    private Integer productNum;

    /**
     * 商品单价
     */
    private BigDecimal productPrice;

    /**
     * 商品+附加项价格
     */
    private BigDecimal totalPrice;

    /**
     * 商品附加项
     */
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
