package com.xdsty.orderbackclient.message;

import java.util.List;

public class OrderRollBackInfo {

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单创建时间
     */
    private long createTime;

    /**
     * 订单结束时间
     */
    private long endTime;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单商品
     */
    private List<OrderRollbackProduct> productList;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<OrderRollbackProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<OrderRollbackProduct> productList) {
        this.productList = productList;
    }
}
