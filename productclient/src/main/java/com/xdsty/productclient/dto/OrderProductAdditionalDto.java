package com.xdsty.productclient.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class OrderProductAdditionalDto implements Serializable {

    private Long additionalId;

    private BigDecimal price;

    public Long getAdditionalId() {
        return additionalId;
    }

    public void setAdditionalId(Long additionalId) {
        this.additionalId = additionalId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductAdditionalDto that = (OrderProductAdditionalDto) o;
        return Objects.equals(additionalId, that.additionalId) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(additionalId, price);
    }
}
