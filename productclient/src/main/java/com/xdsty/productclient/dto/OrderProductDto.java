package com.xdsty.productclient.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class OrderProductDto implements Serializable {

    private Long productId;

    private BigDecimal price;

    private List<OrderProductAdditionalDto> orderProductAdditionals;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<OrderProductAdditionalDto> getOrderProductAdditionals() {
        return orderProductAdditionals;
    }

    public void setOrderProductAdditionals(List<OrderProductAdditionalDto> orderProductAdditionals) {
        this.orderProductAdditionals = orderProductAdditionals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductDto that = (OrderProductDto) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, price);
    }
}
