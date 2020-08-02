package com.xdsty.algorderclient.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车条目
 */
public class CartItemDto implements Serializable {

    private Long productId;

    private Integer num;

    private Long userId;

    private Integer productNum;

    private List<CartAdditionalItem> additionalList;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public List<CartAdditionalItem> getAdditionalList() {
        return additionalList;
    }

    public void setAdditionalList(List<CartAdditionalItem> additionalList) {
        this.additionalList = additionalList;
    }
}
