package com.xdsty.algorderclient.re;

import java.io.Serializable;

public class CartAdditionalItemRe implements Serializable {

    private Long additionalId;

    private Long cartId;

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
