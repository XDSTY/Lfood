package com.xdsty.orderclient.re;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/9/17 9:50
 */
public class OrderListRe implements Serializable {

    private Long orderId;

    private BigDecimal totalPrice;

    private Integer status;

    private List<OrderListProductRe> products;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<OrderListProductRe> getProducts() {
        return products;
    }

    public void setProducts(List<OrderListProductRe> products) {
        this.products = products;
    }
}
