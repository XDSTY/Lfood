package com.xdsty.payclient.re;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserAmountRe implements Serializable {

    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
