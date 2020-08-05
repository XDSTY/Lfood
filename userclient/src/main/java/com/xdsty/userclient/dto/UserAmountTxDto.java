package com.xdsty.userclient.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 张富华
 * @date 2020/8/5 14:49
 */
public class UserAmountTxDto implements Serializable {

    private Long userId;

    private BigDecimal amount;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
