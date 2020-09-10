package com.xdsty.orderclient.re;

import java.io.Serializable;

public class CartAdditionalItemRe implements Serializable {

    private Long additionalId;

    private Long cartId;

    private Integer num;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getAdditionalId() {
        return additionalId;
    }

    public void setAdditionalId(Long additionalId) {
        this.additionalId = additionalId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
}
