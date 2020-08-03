package com.xdsty.api.controller.content;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/7/15 15:17
 */
@Getter
@Setter
public class CartItemContent {

    private Long cartId;

    private Long productId;

    private String productName;

    private String totalPrice;

    private Integer num;

    private String thumbnail;

    private String price;

}
