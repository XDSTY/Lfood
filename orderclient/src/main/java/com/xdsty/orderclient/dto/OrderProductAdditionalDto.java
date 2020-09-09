package com.xdsty.orderclient.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderProductAdditionalDto implements Serializable {

    private Long additionalId;

    private Integer num;

    private BigDecimal price;

    public Long getAdditionalId() {
        return additionalId;
    }

    public void setAdditionalId(Long additionalId) {
        this.additionalId = additionalId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
