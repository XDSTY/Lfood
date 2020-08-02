package com.xdsty.algorderclient.re;

import java.io.Serializable;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/7/15 14:14
 */
public class CartListRe implements Serializable {

    private List<CartItemRe> cartItemRes;

    private List<CartAdditionalItemRe> cartAdditionalItemRes;

    public List<CartItemRe> getCartItemRes() {
        return cartItemRes;
    }

    public void setCartItemRes(List<CartItemRe> cartItemRes) {
        this.cartItemRes = cartItemRes;
    }

    public List<CartAdditionalItemRe> getCartAdditionalItemRes() {
        return cartAdditionalItemRes;
    }

    public void setCartAdditionalItemRes(List<CartAdditionalItemRe> cartAdditionalItemRes) {
        this.cartAdditionalItemRes = cartAdditionalItemRes;
    }
}
