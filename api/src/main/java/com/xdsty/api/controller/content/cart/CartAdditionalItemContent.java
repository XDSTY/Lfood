package com.xdsty.api.controller.content.cart;

import lombok.Getter;
import lombok.Setter;

/**
 * 购物车里商品附加项
 * @author 张富华
 * @date 2020/9/10 17:17
 */
@Getter
@Setter
public class CartAdditionalItemContent {

    private Long id;

    /**
     * 附加项名
     */
    private String name;

    /**
     * 附加项单价
     */
    private String price;

    /**
     * 附加项数量
     */
    private Integer num;

}
