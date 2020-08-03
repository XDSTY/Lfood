package com.xdsty.api.controller.param;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CartItemAddParam {

    @NotNull(message = "商品id不能为空")
    private Long productId;

    @NotNull(message = "商品数量不能为空")
    private Integer productNum;

    private List<CartAdditionalParam> cartItemAddParams;

}
