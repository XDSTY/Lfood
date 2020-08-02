package com.xdsty.algorderclient.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/7/28 9:47
 */
public class OrderAddDto implements Serializable {

    private Long userId;

    private BigDecimal totalPrice;

    private List<OrderProductAddDto> productDtoList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderProductAddDto> getProductDtos() {
        return productDtoList;
    }

    public void setProductDtos(List<OrderProductAddDto> productDtos) {
        this.productDtoList = productDtos;
    }
}
