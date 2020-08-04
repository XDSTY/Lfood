package com.xdsty.orderservice.transaction;

import java.util.Date;

public class OrderTransaction {
    private Long id;

    private String xid;

    private Long branchId;

    private Long orderId;

    private String argsJson;

    private Integer status;

    private Date createTime;

    private Date modifyTime;

    public OrderTransaction() {
    }

    public OrderTransaction(String xid, Long branchId, Integer status) {
        this.xid = xid;
        this.branchId = branchId;
        this.status = status;
    }

    public OrderTransaction(String xid, Long branchId, Integer status, Long orderId) {
        this.xid = xid;
        this.branchId = branchId;
        this.status = status;
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getArgsJson() {
        return argsJson;
    }

    public void setArgsJson(String argsJson) {
        this.argsJson = argsJson;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

}
