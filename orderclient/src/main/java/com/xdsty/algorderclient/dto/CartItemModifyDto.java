package com.xdsty.algorderclient.dto;

import java.io.Serializable;

public class CartItemModifyDto implements Serializable {

    private Long cartId;

    private Long productId;

    /**
     * 变更数量 减一或加一
     */
    private Integer modifyNum;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getModifyNum() {
        return modifyNum;
    }

    public void setModifyNum(Integer modifyNum) {
        this.modifyNum = modifyNum;
    }
}
