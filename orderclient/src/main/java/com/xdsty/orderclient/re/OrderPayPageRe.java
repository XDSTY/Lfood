package com.xdsty.orderclient.re;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 张富华
 * @date 2020/9/14 16:13
 */
public class OrderPayPageRe implements Serializable {

    /**
     * 订单应付金额
     */
    private BigDecimal shouldPayPrice;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 订单支付截止时间
     */
    private Date endTime;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单是否正常
     */
    private Boolean valid;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public BigDecimal getShouldPayPrice() {
        return shouldPayPrice;
    }

    public void setShouldPayPrice(BigDecimal shouldPayPrice) {
        this.shouldPayPrice = shouldPayPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
