package com.xdsty.orderservice.entity;

/**
 * @author 张富华
 * @date 2020/7/9 17:03
 */
public class CartAdditional {

    private Long id;

    private Long cartId;

    private Integer num;

    private Long additionalId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

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
}
