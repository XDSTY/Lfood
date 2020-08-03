package com.xdsty.api.controller.param;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDelParam {

    @NotNull(message = "购物车id不能为空")
    private Long cartId;

    @NotNull(message = "商品id不能为空")
    private Long productId;

}
